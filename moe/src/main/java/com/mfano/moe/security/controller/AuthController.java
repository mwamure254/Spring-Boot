package com.mfano.moe.security.controller;

import com.mfano.moe.security.config.CustomUserDetails;
import com.mfano.moe.security.config.UserDto;
import com.mfano.moe.security.model.Profile;
import com.mfano.moe.security.model.User;
import com.mfano.moe.security.service.AuditService;
import com.mfano.moe.security.service.ProfileService;
import com.mfano.moe.security.service.RoleService;
import com.mfano.moe.security.service.UserService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuditService auditService;

    private String msg = "security/message";
    private final String login = "redirect:/login";

    // dashboard api
    @GetMapping("/dashboard")
    public String redirectAfterLogin(Authentication auth, RedirectAttributes red) {

        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            red.addFlashAttribute("error", "Invalid Username or Password.");
            return "security/login";
        }

        CustomUserDetails u = (CustomUserDetails) auth.getPrincipal();
        // Check if user is enabled
        if (!u.isEnabled()) {
            red.addFlashAttribute("error", "user not verified");
            return "security/login";
        }

        // Extract roles
        Set<String> roles = u.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // If user is not assigned any role
        if (roles.isEmpty()) {
            red.addFlashAttribute("error", "contact the system admin");
            return "security/login";
        }

        // Update Logs
        auditService.record("user_login", "user", "User " + u.getUsername() + " logged in");

        // Redirect based on role priority
        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (roles.contains("ROLE_DIRECTOR")) {
            return "redirect:/director/dashboard";
        } else if (roles.contains("ROLE_HOI")) {
            return "redirect:/hoi/dashboard";
        } else if (roles.contains("ROLE_USER")) {
            return "redirect:/user/dashboard";
        }

        // Fallback
        red.addFlashAttribute("error", "Contact the system admin.");
        return "security/login";
    }

    // register user
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("userDto", new UserDto());
        return "security/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserDto userDto, RedirectAttributes red) {
        try {
            userService.registerUser(userDto.getEmail(), userDto.getPassword(), userDto.getRole());
            red.addFlashAttribute("message", "Registration successful. Check your email for verification link.");
            return msg;
        } catch (Exception e) {
            red.addFlashAttribute("error", e.getMessage());
            return "security/register";
        }
    }

    // login user
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            RedirectAttributes red,
            Authentication authentication) {

        // Logout confirmation
        if (logout != null) {
            red.addFlashAttribute("message", "Logged out successfully.");
        }

        // If user is already logged in → redirect to dashboard
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails) {
            red.addFlashAttribute("message", "Already logged in.");
            return "redirect:/dashboard";
        }

        // Error from Spring Security (bad credentials or disabled)
        if (error != null) {
            red.addFlashAttribute("error", "Check your credentials and try again.");
        }

        return "security/login"; // Return login view
    }

    // update user image
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image/update/{userid}")
    public String imageUpdate(@PathVariable Long userid, @RequestParam("image") MultipartFile file,
            RedirectAttributes red) {
        try {
            profileService.updateProfileImage(userid, file, red);
        } catch (IOException e) {
            red.addFlashAttribute("error", e.getMessage());
        }
        auditService.record("update_image", "user id=" + userid, "Updated their profile image");
        red.addFlashAttribute("message", "Image updated successfully.");
        return "redirect:/profile";
    }

    // delete user image
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image/delete/{userid}")
    public String imageDelete(@PathVariable Long userid, RedirectAttributes red) {
        try {
            profileService.deleteProfileImage(userid, red);
        } catch (IOException e) {
            red.addFlashAttribute("error", e.getMessage());
        }
        auditService.record("delete_image", "user id=" + userid, "Deleted their profile image");
        red.addFlashAttribute("message", "Image deleted successfully.");
        return "redirect:/profile";
    }

    // profile
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String userProfile(Authentication auth, RedirectAttributes red, Model model) {

        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            red.addFlashAttribute("error", "user not authenticated");
            return "redirect:/login";
        } else {
            userService.redirectUser(auth, model);
            red.addFlashAttribute("message", "Welcome to your profile");
            return "security/profile";
        }
    }

    // profile/update @PreAuthorize("isAuthenticated()")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/update")
    public String userProfileUpdate(Authentication auth, @ModelAttribute("profile") Profile profile) {

        CustomUserDetails u = (CustomUserDetails) auth.getPrincipal();

        profileService.update(u.getId(), profile);
        auditService.record("update_profile", "user id=" + u.getId(), "Updated their profile");
        return "redirect:/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public String logout() {
        return login;
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token, RedirectAttributes red) {
        String result = userService.validateVerificationToken(token);
        if ("valid".equals(result)) {
            red.addFlashAttribute("message", "Email verified! You can now login.");
            return msg;
        } else if ("expired".equals(result)) {
            red.addFlashAttribute("error", "Token expired. Please register again.");
            return msg;
        } else {
            red.addFlashAttribute("error", "Invalid token.");
            return msg;
        }
    }

    @GetMapping("/resend")
    public String resendForm() {
        return "security/resend";
    }

    @PostMapping("/resend")
    public String resendSubmit(@RequestParam("email") String email, RedirectAttributes red) {
        User user = userService.findByEmail(email);
        if (user == null) {
            red.addFlashAttribute("error", "No account with that email.");
            return "security/resend";
        }

        if (user.isEnabled()) {
            red.addFlashAttribute("message", "Email already verified. You can login.");
            return msg;
        }
        userService.createAndSendToken(user);
        red.addFlashAttribute("message", "Verification email resent. Check your inbox.");
        return msg;
    }

    // Forgot/reset endpoints
    @GetMapping("/forgot")
    public String forgotForm() {
        return "security/forgot";
    }

    @PostMapping("/forgot")
    public String forgotSubmit(@RequestParam String email, RedirectAttributes model) {
        try {
            userService.createPasswordResetToken(email);
            model.addFlashAttribute("message", "A reset link was sent to " + email + ".");
        } catch (UsernameNotFoundException e) {
            model.addFlashAttribute("error", e.getMessage());
        }
        return login;
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
            RedirectAttributes red) {
        var optUser = userService.getUserByPasswordResetToken(token);
        if (optUser.isEmpty()) {
            red.addFlashAttribute("error", "Invalid token.");
            return msg;
        }
        userService.changePassword(optUser.get(), password);
        red.addFlashAttribute("message", "Password changed. You can now login.");
        return msg;
    }

    // Reset user password
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reset")
    public String resetPassword(Authentication auth, @RequestParam String password, @RequestParam String NP,
            RedirectAttributes red) {
        CustomUserDetails u = (CustomUserDetails) auth.getPrincipal();
        User user = userService.findById(u.getId());
        if (!NP.equals(password) || NP.isEmpty() || password.isEmpty()) {
            red.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/profile";
        }

        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            auditService.record("reset_password", "user id=" + user.getId(), "Reset password");
            red.addFlashAttribute("message", "Password reset successful");
            return "redirect:/profile";
        } else {
            red.addFlashAttribute("error", "Failed to reset password");
            return "redirect:/profile";
        }
    }

}
