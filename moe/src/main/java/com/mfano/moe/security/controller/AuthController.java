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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final ProfileService profileService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleService roleService;
    @Autowired
    private final AuditService auditService;

    private String msg = "security/message";
    private final String login = "redirect:/login";

    @GetMapping("/dashboard")
    public String redirectAfterLogin(Authentication auth, Model model) {
        Object principal = auth.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            model.addAttribute("error", "Invalid Username or Password.");
            return login;
        }

        CustomUserDetails u = (CustomUserDetails) principal;
        // Check if user is enabled
        if (!u.isEnabled()) {
            model.addAttribute("error", "user not verified");
            return login;
        }

        // Extract roles
        Set<String> roles = u.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // If user is not assigned any role
        if (roles.isEmpty()) {
            model.addAttribute("error", "contact the system admin");
            return login;
        }

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
        model.addAttribute("error", "Please contact the system admin");
        return login;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("userDto", new UserDto());
        return "security/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute UserDto userDto, Model model) {
        try {
            userService.registerUser(userDto.getEmail(), userDto.getPassword(), userDto.getRole());
            model.addAttribute("message", "Registration successful. Check your email for verification link.");
            return msg;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "security/register";
        }
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model,
            Authentication authentication) {

        // If user is already logged in → redirect to dashboard
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails) {
            return "redirect:/dashboard";
        } 

        // Error from Spring Security (bad credentials or disabled)
        if (error != null) {
            model.addAttribute("error", "Check your credentials and try again.");
        }

        // Logout confirmation
        if (logout != null) {
            model.addAttribute("message", "You have been logged out.");
        }

        return "security/login"; // Return login view
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image/update/{userid}")
    public String imageUpdate(@PathVariable Long userid, @RequestParam("image") MultipartFile file, Model model) {
        try {
            profileService.updateProfileImage(userid, file, model);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
        }
        auditService.record("update_image", "user id=" + userid, "Updated their profile image");
        return "redirect:/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image/delete/{userid}")
    public String imageDelete(@PathVariable Long userid, Model model) {
        try {
            profileService.deleteProfileImage(userid, model);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
        }
        auditService.record("delete_image", "user id=" + userid, "Deleted their profile image");
        return "redirect:/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String userProfile(Authentication auth, Model model) {
        userService.redirectUser(auth, model);
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            model.addAttribute("error", "user not authenticated");
            return "redirect:/login";
        } else {
            userService.redirectUser(auth, model);
            model.addAttribute("message", "Welcome to your profile");

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
    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("message", "You have been logged out successfully");
        return "redirect:/login";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token, Model model) {
        String result = userService.validateVerificationToken(token);
        if ("valid".equals(result)) {
            model.addAttribute("message", "Email verified! You can now login.");
            return msg;
        } else if ("expired".equals(result)) {
            model.addAttribute("error", "Token expired. Please register again.");
            return msg;
        } else {
            model.addAttribute("error", "Invalid token.");
            return msg;
        }
    }

    @GetMapping("/resend")
    public String resendForm() {
        return "security/resend";
    }

    @PostMapping("/resend")
    public String resendSubmit(@RequestParam("email") String email, Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "No account with that email.");
            return "security/resend";
        }

        if (user.isEnabled()) {
            model.addAttribute("message", "Email already verified. You can login.");
            return msg;
        }
        userService.createAndSendToken(user);
        model.addAttribute("message", "Verification email resent. Check your inbox.");
        return msg;
    }

    // Forgot/reset endpoints
    @GetMapping("/forgot")
    public String forgotForm() {
        return "security/forgot";
    }

    @PostMapping("/forgot")
    public String forgotSubmit(@RequestParam String email, Model model) {
        try {
            userService.createPasswordResetToken(email);
            model.addAttribute("message", "If an account exists, a reset link was sent.");
        } catch (Exception e) {
            model.addAttribute("message", "If an account exists, a reset link was sent.");
        }
        return msg;
    }

    @GetMapping("/password-reset")
    public String resetPasswordForm(@RequestParam("token") String token, Model model) {
        String res = userService.validatePasswordResetToken(token);
        if ("valid".equals(res)) {
            model.addAttribute("token", token);
            return "security/reset-password";
        } else if ("expired".equals(res)) {
            model.addAttribute("error", "Token expired.");
            return msg;
        } else {
            model.addAttribute("error", "Invalid token.");
            return msg;
        }
    }

    @PostMapping("/reset-password")
    public String resetPasswordSubmit(@RequestParam String token, @RequestParam String password, Model model) {
        var optUser = userService.getUserByPasswordResetToken(token);
        if (optUser.isEmpty()) {
            model.addAttribute("error", "Invalid token.");
            return msg;
        }
        userService.changePassword(optUser.get(), password);
        model.addAttribute("message", "Password changed. You can now login.");
        return msg;
    }

    // Reset user password
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reset")
    public String resetPassword(Authentication auth, @RequestParam String password, @RequestParam String NP,
            Model model) {
        CustomUserDetails u = (CustomUserDetails) auth.getPrincipal();
        User user = userService.findById(u.getId());
        if (!NP.equals(password) || NP.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Passwords do not match");
            return "redirect:/profile";
        }

        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            auditService.record("reset_password", "user id=" + user.getId(), "Reset password");
            model.addAttribute("message", "Password reset successful");
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "Failed to reset password");
            return "redirect:/profile";
        }
    }

}
