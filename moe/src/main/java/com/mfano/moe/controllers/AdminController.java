package com.mfano.moe.controllers;

import com.mfano.moe.models.ICategory;
import com.mfano.moe.models.ILevel;
import com.mfano.moe.models.UStatus;
import com.mfano.moe.models.IStatus;
import com.mfano.moe.models.SStatus;

import com.mfano.moe.security.config.UserDto;
import com.mfano.moe.security.model.Profile;
import com.mfano.moe.security.model.Role;
import com.mfano.moe.security.model.User;
import com.mfano.moe.security.service.UserService;
import com.mfano.moe.security.service.AuditService;
import com.mfano.moe.security.service.ProfileService;
import com.mfano.moe.security.service.RoleService;
import com.mfano.moe.services.AdminService;
import com.mfano.moe.services.ICategoryService;
import com.mfano.moe.services.IStatusService;
import com.mfano.moe.services.SStatusService;
import com.mfano.moe.services.UStatusService;
import com.mfano.moe.services.ILevelService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@EnableMethodSecurity

@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;
    private final AdminService adminService;
    private final ProfileService profileService;

    // statuses
    private final ICategoryService iCategoryService;
    private final IStatusService iStatusService;
    private final SStatusService sStatusService;
    private final UStatusService uStatusService;
    private final ILevelService iLevelService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model, RedirectAttributes red) {

        userService.redirectUser(auth, model);
        List<User> users = userService.findAll();

        model.addAttribute("userDto", new UserDto());
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("auditEntries", auditService.findAll());

        red.addFlashAttribute("message", "Login successsful.");
        return "admin/dashboard";
    }

    private String redirect = "redirect:/admin/dashboard";
    private String status = "redirect:/admin/editables";

    // Create a new user
    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDto userDto,
            RedirectAttributes red) {
        try {
            userService.registerUser(userDto.getEmail(), userDto.getPassword(), userDto.getRole());
            red.addFlashAttribute("message", "User created and verification email sent.");
            auditService.record("create_user", "admin", "Created user: " + userDto.getEmail());
        } catch (Exception e) {
            red.addFlashAttribute("error", e.getMessage());
        }
        return redirect;
    }

    // get editable page
    @GetMapping("/editables")
    public String editables(Authentication auth, Model model) {

        userService.redirectUser(auth, model);
        return "admin/admin-editables";
    }

    @PostMapping("/status/{option}")
    public String createStatus(@PathVariable String option, RedirectAttributes red, Role role, ILevel level,
            UStatus user, SStatus board, IStatus institute, ICategory category) {
        switch (option) {
            // Create a new role
            case "role":
                roleService.save(role);
                red.addFlashAttribute("message", "Role added successfully");
                break;

            // Create a new level
            case "level":
                iLevelService.save(level);
                red.addFlashAttribute("message", "Level added successfully");
                break;

            // Create a new status
            case "user":
                uStatusService.save(user);
                red.addFlashAttribute("message", "Status added successfully");
                break;

            // Create a new service status
            case "service":
                sStatusService.save(board);
                red.addFlashAttribute("message", "Status added successfully");
                break;

            // Create a new station status
            case "institution":
                iStatusService.save(institute);
                red.addFlashAttribute("message", "Status added successfully");
                break;

            // Create a new category
            case "category":
                iCategoryService.save(category);
                red.addFlashAttribute("message", "Category added successfully");
                break;

            default:
                red.addFlashAttribute("error", "An error occured, please try again later.");
                break;
        }

        return status;
    }

    // Reset user password
    @PostMapping("/reset-password/{id}")
    public String resetPassword(@PathVariable Long id, @RequestParam String newPassword,
         RedirectAttributes red) {
        User user = userService.findById(id);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);
            auditService.record("reset_password", "admin", "Reset password for user id=" + id);
            red.addFlashAttribute("message", "Passwords reset successfully");
        }
        return redirect;
    }

    // Reset user password
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reset/{userid}")
    public String resetPassword(Authentication auth, @PathVariable Long userid, @RequestParam String password,
            @RequestParam String NP,
            RedirectAttributes red) {
        userService.redirectUser(auth, red);
        User user = userService.findById(userid);
        if (!NP.equals(password) || NP.isEmpty() || password.isEmpty()) {
            red.addFlashAttribute("error", "Passwords do not match");
        }

        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            auditService.record("reset_password", "Admin", "Reset password for user id=" + user.getId());
            red.addFlashAttribute("message", "Password reset successful");
        } else {
            red.addFlashAttribute("error", "Failed to reset password");
        }

        return "redirect:/admin/profile/{userid}";
    }

    // manage user roles
    @GetMapping("/{option}/{id}")
    public String manageRoles(Authentication auth, @PathVariable String option, @PathVariable Long id, 
        Model model, RedirectAttributes red) {
        userService.redirectUser(auth, model);
        User user = userService.findById(id);
        model.addAttribute("user", user);

        String dir = redirect;
        switch (option) {
            case "manage-user":
                model.addAttribute("userRoles", roleService.getUserRoles(user));
                model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
                dir = "admin/manage-roles";
                break;

            case "profile":
                // Add user info to model (for Thymeleaf dashboard pages)
                model.addAttribute("id1", user.getId());
                model.addAttribute("username1", user.getUsername());
                model.addAttribute("password1", user.getPassword());
                model.addAttribute("roles1", user.getRoles());

                profileService.checkProfile(id, model);
                dir = "admin/manage-profile";
                break;

            // Resend verification link
            case "resend":
                if (user != null && !user.isEnabled()) {
                    userService.createAndSendToken(user);
                    auditService.record("RESEND_VERIFICATION", "admin", "Resent token to user id=" + id);
                    red.addFlashAttribute("message", "Link resent to user");
                }
                break;

            // Delete user
            case "delete":
                try {
                    userService.delete(id);
                    auditService.record("delete_user", "admin", "Deleted user id=" + id);
                    red.addFlashAttribute("message", "user successfully deleted");
                } catch (Exception e) {
                    red.addFlashAttribute("error", "Sorry! Failed to delete user");
                }
                break;

            // Toggle user
            case "toggle":
                if (user != null) {
                    user.setEnabled(!user.isEnabled());
                    userService.save(user);
                    auditService.record("toggle_user", "admin",
                            "Toggled user: " + user.getEmail() + " to enabled=" + user.isEnabled());
                    red.addFlashAttribute("message", "Toggled user successfully");
                }
                break;

        }

        return dir;
    }

    // profile/update @PreAuthorize("isAuthenticated()")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/update/{userid}")
    public String userProfileUpdate(Authentication auth, @PathVariable Long userid,
            @ModelAttribute("profile") Profile profile, RedirectAttributes red) {

        userService.redirectUser(auth, red);
        profileService.update(userid, profile);
        auditService.record("update_profile", "admin", "Updated the profile of user id=" + userid);
        red.addFlashAttribute("message", "Profile updated successfully");
        return "redirect:/admin/profile/{userid}";
    }

    // Update profile image
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image/update/{userid}")
    public String imageUpdate(Authentication auth, @PathVariable Long userid,
            @RequestParam("image") MultipartFile file, RedirectAttributes red) {
        userService.redirectUser(auth, red);
        try {
            profileService.updateProfileImage(userid, file, red);
            auditService.record("update_image", "admin", "Updated the profile image  of user id=" + userid);
            red.addFlashAttribute("message", "Image updated successfully");
        } catch (IOException e) {
            red.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/profile/{userid}";
    }

    // Delete profile image
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image/delete/{userid}")
    public String imageDelete(Authentication auth, @PathVariable Long userid, RedirectAttributes red) {
        userService.redirectUser(auth, red);

        try {
            profileService.deleteProfileImage(userid, red);
            auditService.record("delete_image", "admin", "Deleted the profile image of user id=" + userid);
            red.addFlashAttribute("message", "Image deleted successfully");
        } catch (IOException e) {
            red.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/profile/{userid}";
    }

    // Assign Remove roles
    @PostMapping("/{option}/{userId}/{roleId}")
    public String userRole(Authentication auth, @PathVariable Long userId, @PathVariable String option,
            @PathVariable Long roleId, RedirectAttributes red) {

        userService.redirectUser(auth, red);
        switch (option) {
            case "assign-role":
                adminService.assignRoleToUser(userId, roleId);
                auditService.record("update_role", "admin", "Assigned user id=" + userId + " role id=" + roleId);
                red.addFlashAttribute("message", "Action successful");
                break;

            case "remove-role":
                adminService.removeRoleFromUser(userId, roleId);
                auditService.record("update_role", "admin", "Revoked role id=" + roleId + " from user id=" + userId);
                red.addFlashAttribute("message", "Action successful");
                break;

            default:
                red.addFlashAttribute("error", "Action failed");
                break;
        }

        return "redirect:/admin/manage-user/{userId}";
    }

}
