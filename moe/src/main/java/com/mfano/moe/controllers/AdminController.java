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
import com.mfano.moe.services.AdminService;
import com.mfano.moe.security.service.AuditService;
import com.mfano.moe.security.service.ProfileService;
import com.mfano.moe.security.service.RoleService;

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
    public String dashboard(Authentication auth, Model model) {

        userService.redirectUser(auth, model);
        List<User> users = userService.findAll();

        model.addAttribute("userDto", new UserDto());
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("auditEntries", auditService.findAll());

        model.addAttribute("message", "login successsful");
        return "admin/dashboard";
    }

    private String redirect = "redirect:/admin/dashboard";
    private String status = "redirect:/admin/editables";

    // Create a new user
    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDto userDto,
            Model model) {
        try {
            userService.registerUser(userDto.getEmail(), userDto.getPassword(), userDto.getRole());
            model.addAttribute("message", "User created and verification email sent.");
            auditService.record("create_user", "admin", "Created user: " + userDto.getEmail());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return redirect;
    }

    // Create a new role
    @PostMapping("/status/role")
    public String createStatus(Model model, Role role) {
        roleService.save(role);
        model.addAttribute("message", "Role added successfully");

        return status;
    }

    // Create a new level
    @PostMapping("/status/level")
    public String createStatus(Model model, ILevel level) {
        iLevelService.save(level);
        model.addAttribute("message", "Level added successfully");

        return status;
    }

    // Create a new status
    @PostMapping("/status/user")
    public String createStatus(Model model, UStatus user) {
        uStatusService.save(user);
        model.addAttribute("message", "Status added successfully");

        return status;
    }

    // Create a new service status
    @PostMapping("/status/service")
    public String createStatus(Model model, SStatus board) {
        sStatusService.save(board);
        model.addAttribute("message", "Status added successfully");

        return status;
    }

    // Create a new station status
    @PostMapping("/status/institution")
    public String createStatus(Model model, IStatus institute) {
        iStatusService.save(institute);
        model.addAttribute("message", "Status added successfully");

        return status;
    }

    // Create a new category
    @PostMapping("/status/category")
    public String createStatus(Model model, ICategory category) {
        iCategoryService.save(category);
        model.addAttribute("message", "Category added successfully");

        return status;
    }

    // get editable page
    @GetMapping("/editables")
    public String editables(Authentication auth, Model model) {

        userService.redirectUser(auth, model);
        return "admin/admin-editables";
    }

    // Enable/Disable user
    @GetMapping("/toggle/{id}")
    public String toggleUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            user.setEnabled(!user.isEnabled());
            userService.save(user);
            auditService.record("toggle_user", "admin",
                    "Toggled user: " + user.getEmail() + " to enabled=" + user.isEnabled());
        }
        return redirect;
    }

    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        try {
            userService.delete(id);
            auditService.record("delete_user", "admin", "Deleted user id=" + id);
            model.addAttribute("message", "user successfully deleted");
            return redirect;
        } catch (Exception e) {
            model.addAttribute("error", "Sorry! Failed to delete user");
            return redirect;
        }
    }

    // Change role
    @PostMapping("/update-role/{id}")
    public String updateRole(@PathVariable Long id, @RequestParam String role) {
        User user = userService.findById(id);
        if (user != null) {
            userService.save(user);
            auditService.record("update_role", "admin", "Updated role for user id=" + id + " to " + role);
        }
        return redirect;
    }

    // Reset user password
    @PostMapping("/reset-password/{id}")
    public String resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        User user = userService.findById(id);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);
            auditService.record("reset_password", "admin", "Reset password for user id=" + id);
        }
        return redirect;
    }

    // Reset user password
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reset/{userid}")
    public String resetPassword(Authentication auth, @PathVariable Long userid, @RequestParam String password,
            @RequestParam String NP,
            Model model) {
        userService.redirectUser(auth, model);
        User user = userService.findById(userid);
        if (!NP.equals(password) || NP.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Passwords do not match");
            return "redirect:admin/profile/{userid}";
        }

        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userService.save(user);
            auditService.record("reset_password", "Admin", "Reset password for user id=" + user.getId());
            model.addAttribute("message", "Password reset successful");
            return "redirect:/admin/profile/{userid}";
        } else {
            model.addAttribute("error", "Failed to reset password");
            return "redirect:/admin/profile/{userid}";
        }
    }

    @GetMapping("/resend/{id}")
    public String adminResend(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null && !user.isEnabled()) {
            userService.createAndSendToken(user);
            auditService.record("RESEND_VERIFICATION", "admin", "Resent token to user id=" + id);
        }
        return redirect;
    }

    @GetMapping("/manage-user/{id}")
    public String manageRoles(Authentication auth, @PathVariable Long id, Model model) {
        userService.redirectUser(auth, model);
        User user = userService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));

        return "admin/manage-roles";
    }

    @GetMapping("/profile/{userid}")
    public String getProfile(Authentication auth, @PathVariable Long userid, Model model) {
        userService.redirectUser(auth, model);
        User user = userService.findById(userid);
        model.addAttribute("user", user);

        // Add user info to model (for Thymeleaf dashboard pages)
        model.addAttribute("id1", user.getId());
        model.addAttribute("username1", user.getUsername());
        model.addAttribute("password1", user.getPassword());
        model.addAttribute("roles1", user.getRoles());

        profileService.checkProfile(userid, model);
        return "admin/manage-profile";
    }

    // profile/update @PreAuthorize("isAuthenticated()")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/update/{userid}")
    public String userProfileUpdate(Authentication auth, @PathVariable Long userid,
            @ModelAttribute("profile") Profile profile, Model model) {

        userService.redirectUser(auth, model);
        profileService.update(userid, profile);
        auditService.record("update_profile", "admin", "Updated the profile of user id=" + userid);
        return "redirect:/admin/profile/{userid}";
    }

    // update profile image
    @PostMapping("/image/update/{userid}")
    public String imageUpdate(Authentication auth, @PathVariable Long userid, @RequestParam("image") MultipartFile file,
            Model model) {
        userService.redirectUser(auth, model);
        try {
            profileService.updateProfileImage(userid, file, model);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
        }
        auditService.record("update_image", "admin", "Updated the profile image  of user id=" + userid);
        return "redirect:/admin/profile/{userid}";
    }

    // delete profile image
    @PostMapping("/image/delete/{userid}")
    public String imageDelete(Authentication auth, @PathVariable Long userid, Model model) {
        userService.redirectUser(auth, model);
        try {
            profileService.deleteProfileImage(userid, model);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
        }
        auditService.record("delete_image", "admin", "Deleted the profile image of user id=" + userid);
        return "redirect:/admin/profile/{userid}";
    }

    @PostMapping("/assign-role/{userId}/{roleId}")
    public String assignRole(Authentication auth, @PathVariable Long userId, @PathVariable Long roleId, Model model) {
        userService.redirectUser(auth, model);
        adminService.assignRoleToUser(userId, roleId);
        auditService.record("update_role", "admin", "Assigned user id=" + userId + " role id=" + roleId);
        return "redirect:/admin/manage-role/{userId}";
    }

    @PostMapping("/remove-role/{userId}/{roleId}")
    public String removeRole(Authentication auth, @PathVariable Long userId, @PathVariable Long roleId, Model model) {
        userService.redirectUser(auth, model);
        adminService.removeRoleFromUser(userId, roleId);
        auditService.record("update_role", "admin", "Revoked role id=" + roleId + " from user id=" + userId);
        return "redirect:/admin/manage-role/{userId}";
    }

}
