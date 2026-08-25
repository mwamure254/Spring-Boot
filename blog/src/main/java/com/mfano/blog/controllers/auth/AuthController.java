package com.mfano.blog.controllers.auth;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.blog.config.CustomUserDetails;
import com.mfano.blog.dtos.UserDto;
import com.mfano.blog.models.security.User;
import com.mfano.blog.repositories.security.RoleRepository;
import com.mfano.blog.services.security.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RoleRepository roleRepo;
    private String msg = "security/message";
    private final String login = "redirect:/login?error";

    @GetMapping("/")
    public String redirectAfterLogin(Authentication auth, RedirectAttributes model) {
        if (auth == null || !auth.isAuthenticated()) {
            model.addFlashAttribute("error", "user not authenticated");
            return login;
        }
        Object principal = auth.getPrincipal();
        // Extract roles
        if (principal instanceof CustomUserDetails u) {
            Set<String> roles = u.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            if (!u.isEnabled()) {
                model.addFlashAttribute("error", "user not verified");
                return login;
            } else if (roles.isEmpty()) {
                model.addFlashAttribute("error", "contact the system admin");
                return login;
            }

            // Redirect based on role priority
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/admin/dashboard";
            } else if (roles.contains("ROLE_AUTHOR")) {
                return "redirect:/author/dashboard";
            } else if (roles.contains("ROLE_EDITOR")) {
                return "redirect:/editor/dashboard";
            } else if (roles.contains("ROLE_USER")) {
                return "redirect:/user/dashboard";
            }
        } else {
            model.addFlashAttribute("error", "invalid user");
            return login;
        }
        // Fallback
        model.addFlashAttribute("error", "Please contact the system admin");
        return login;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("userDto", new UserDto());
        return "security/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserDto userDto, RedirectAttributes model) {
        try {
            userService.registerUser(userDto.getEmail(), userDto.getPassword(), userDto.getRoles());
            model.addFlashAttribute("message", "Registration successful. Check your email for verification link.");
            return msg;
        } catch (Exception e) {
            model.addFlashAttribute("error", e.getMessage());
            return "security/register";
        }
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            RedirectAttributes model,
            Authentication authentication) {

        // If user is already logged in → redirect to dashboard
        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof CustomUserDetails) {
            return "redirect:/dashboard";
        }

        // Logout confirmation
        if (logout != null) {
            model.addFlashAttribute("message", "You have been logged out.");
        }

        return "security/login"; // Return login view
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password,
            RedirectAttributes model) {
        if (userService.findByEmail(username) == null && userService.findByUsername(username) == null) {
            model.addFlashAttribute("error", "Invalid email or username.");
            return login;
        }
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String userProfile(Authentication auth, Model model, RedirectAttributes red) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails u)) {
            red.addFlashAttribute("error", "user not authenticated");
            return "redirect:/login";
        }
        // Add user info to model (for Thymeleaf dashboard pages)
        model.addAttribute("id", u.getId());
        model.addAttribute("username", u.getUsername());
        model.addAttribute("email", u.getEmail());
        model.addAttribute("firstname", u.getFin());
        model.addAttribute("lastname", u.getLan());
        model.addAttribute("roles", u.getRoles());

        return "security/profile";
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes model) {
        model.addFlashAttribute("message", "You have been logged out successfully");
        return "redirect:/login";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token, RedirectAttributes model) {
        String result = userService.validateVerificationToken(token);
        if ("valid".equals(result)) {
            model.addFlashAttribute("message", "Email verified! You can now login.");
        } else if ("expired".equals(result)) {
            model.addFlashAttribute("error", "Token expired. Please register again.");
        } else {
            model.addFlashAttribute("error", "Invalid token.");

        }
        return msg;
    }

    @GetMapping("/resend")
    public String resendForm() {
        return "security/resend";
    }

    @PostMapping("/resend")
    public String resendSubmit(@RequestParam("email") String email, RedirectAttributes model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addFlashAttribute("error", "No account with that email.");
            return "redirect:/resend";
        }

        if (user.isEnabled()) {
            model.addFlashAttribute("message", "Email already verified. You can login.");
            return msg;
        }
        userService.createAndSendToken(user);
        model.addFlashAttribute("message", "Verification email resent. Check your inbox.");
        return msg;
    }

    // Forgot/reset endpoints
    @GetMapping("/forgot")
    public String forgotForm() {
        return "security/forgot";
    }

    @PostMapping("/forgot")
    public String forgotSubmit(@RequestParam String email, RedirectAttributes model) {
        if (userService.findByEmail(email) == null) {
            model.addFlashAttribute("error", "No account matches the email address.");
            return "redirect:/forgot";
        }

        try {
            userService.createPasswordResetToken(email);
            model.addFlashAttribute("message", "If an account exists, a reset link was sent.");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Something went wrong, please try again.");
            return "redirect:/forgot";
        }
        return msg;
    }

    @GetMapping("/password-reset")
    public String resetPasswordForm(@RequestParam("token") String token, Model model, RedirectAttributes red) {
        String res = userService.validatePasswordResetToken(token);
        if ("valid".equals(res)) {
            model.addAttribute("token", token);
            return "security/reset-password";
        } else if ("expired".equals(res)) {
            red.addFlashAttribute("error", "Token expired.");
            return msg;
        } else {
            red.addFlashAttribute("error", "Invalid token.");
            return msg;
        }
    }

    @PostMapping("/reset-password")
    public String resetPasswordSubmit(@RequestParam String token, @RequestParam String password,
            RedirectAttributes model) {
        var optUser = userService.getUserByPasswordResetToken(token);
        if (optUser.isEmpty()) {
            model.addFlashAttribute("error", "Invalid token.");
            return msg;
        }
        userService.changePassword(optUser.get(), password);
        model.addFlashAttribute("message", "Password changed. You can now login.");
        return msg;
    }
}
