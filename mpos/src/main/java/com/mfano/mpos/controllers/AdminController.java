package com.mfano.mpos.controllers;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.dtos.UserDto;
import com.mfano.mpos.models.security.Role;
import com.mfano.mpos.models.security.User;
import com.mfano.mpos.models.security.Profile;
import com.mfano.mpos.services.BranchService;
import com.mfano.mpos.services.security.AuditService;
import com.mfano.mpos.services.security.ProfileService;
import com.mfano.mpos.services.ProductService;
import com.mfano.mpos.services.security.RoleService;
import com.mfano.mpos.services.security.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    // private final PostService postService;
    private final ProfileService profileService;
    private final AuditService auditService;
    private final RoleService roleService;
    private final BranchService storeService;

     // manage /GET/*  module
    @GetMapping("/{option}")
    public String manage(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable String option, Model red) {
        red.addAttribute("profile", profileService.checkProfile(auth.getId()));

        String dir = "redirect";
        switch (option) {
            case "dashboard":
                red.addAttribute("users", userService.findAll());
                red.addAttribute("stores", storeService.findAll());
                red.addAttribute("roles", roleService.findAll());
                red.addAttribute("audits", auditService.findAll());
                dir = "admin/index";
                break;

            case "users":
                red.addAttribute("userDto", new UserDto());
                Long storeId = auth.getBranch().getId();

                if (storeId != null) {
                    // red.addAttribute("users", userService.findByBranch_Id(storeId));
                    red.addAttribute("users", userService.findAll());

                } else {
                    red.addAttribute("users", userService.findAll());
                }
                red.addAttribute("stores", storeService.findAll());
                red.addAttribute("roles", roleService.findAll());
                dir = "admin/users";
                break;

            case "stores":
                red.addAttribute("users", userService.findAll());
                red.addAttribute("stores", storeService.findAll());
                dir = "admin/stores";
                break;

            case "roles":
                red.addAttribute("roles", roleService.findAll());
                red.addAttribute("stores", storeService.findAll());
                dir = "admin/roles";
                break;    
        }

        return dir;
    }

    //Role saving
    @PostMapping("/roles/save")
    public String saveRole(@AuthenticationPrincipal CustomUserDetails auth, @ModelAttribute Role role, RedirectAttributes red) {
        try {
            roleService.save(role);
            auditService.record("CREATE_ROLE", auth.getEmail() + " Created role: " + role.getName());
            red.addFlashAttribute("message", "Role created successfully!");
        } catch (Exception e) {
            red.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/roles";
    }

    //Role update
    @PostMapping("/roles/update/{id}")
    public String updateRole(
            @PathVariable Long id,
            @Valid Role role,
            RedirectAttributes redirectAttributes){

        roleService.update(id, role);

        redirectAttributes.addFlashAttribute(
                "message",
                "Role updated successfully."
        );

        return "redirect:/admin/roles";
    }

    // manage /roles/*  module
    @PostMapping("/roles/{option}/{id}")
    public String manage(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable String option, 
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        String dir = "redirect";
        switch (option) {
            case "delete":
                roleService.deleteById(id);
                auditService.record("delete_role", auth.getEmail() + " Deleted role id=" + id);
                redirectAttributes.addFlashAttribute(
                    "message",
                    "Role deleted successfully."
                 );
                dir = "redirect:/admin/roles";
                break;

            case "toggle":
                roleService.toggleActive(id);
                auditService.record("toggle_role", auth.getEmail() + " toggled role id=" + id);
                redirectAttributes.addFlashAttribute(
                    "message",
                    "Role toggled successfully."
                );
                dir = "redirect:/admin/roles";
                break;   
        }

        return dir;
    }

    //save user
    @PostMapping("/users/save")
    public String saveUser(@AuthenticationPrincipal CustomUserDetails auth, @ModelAttribute UserDto userDto, RedirectAttributes red) {
        try {
            userService.registerUser(userDto);
            auditService.record("CREATE_USER", auth.getEmail() + " Created user: " + userDto.getEmail());
            red.addFlashAttribute("message", "User created successfully!");
        } catch (Exception e) {
            red.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    //update user
    @PostMapping("/users/update/{id}")
    public String updateUser(@AuthenticationPrincipal CustomUserDetails auth,
            @PathVariable Long id,
            @Valid UserDto userDto,
            RedirectAttributes redirectAttributes){

        userService.update(id, userDto);
        auditService.record("UPDATE_USER", auth.getEmail() + " Updated user: " + userDto.getEmail());
        redirectAttributes.addFlashAttribute(
                "message",
                "User updated successfully."
        );

        return "redirect:/admin/users";
    }

    @GetMapping("/manage/{id}")
    public String manageProfile(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("profile", profileService.checkProfile(auth.getId()));
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "admin/user-roles";
    }

     // manage /user route
    @PostMapping("/users/{option}/{id}")
    public String manageGetter(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable String option, @PathVariable Long id, 
        Model model, RedirectAttributes red) {

        User user = userService.findById(id);
        model.addAttribute("user", user);

        String dir = "redirect";
        switch (option) {

            case "profile":
                // Add user info to model (for Thymeleaf dashboard pages)
                model.addAttribute("profile", profileService.checkProfile(auth.getId()));
                model.addAttribute("profile1", profileService.checkProfile(user.getId()));
                dir = "admin/user-profile";
                break;

            // Resend verification link
            case "resend":
                if (user != null && !user.isEnabled()) {
                    userService.createAndSendToken(user);
                    auditService.record("RESEND_VERIFICATION", auth.getEmail() + " Resent token to user id=" + id);
                    red.addFlashAttribute("message", "Link resent to user");
                    dir = "redirect:/admin/users";
                }
                else{
                    red.addFlashAttribute("error", "Sorry! Failed to send link.");
                    dir = "redirect:/admin/users";
                }
                break;

            // Delete user
            case "delete":
                try {
                    userService.deleteById(id);
                    auditService.record("delete_user", auth.getEmail() + " Deleted user id=" + id);
                    red.addFlashAttribute("message", "user successfully deleted");
                    dir = "redirect:/admin/users";
                } catch (Exception e) {
                    red.addFlashAttribute("error", "Sorry! Failed to delete user");
                    dir = "redirect:/admin/users";
                }
                break;

            // Toggle user
            case "toggle":
                if (user != null) {
                    user.setEnabled(!user.isEnabled());
                    
                    userService.toggleActive(id);
                    auditService.record("toggle_user",auth.getEmail() + " Toggled user: " + user.getEmail() + " to enabled=" + user.isEnabled());
                    red.addFlashAttribute("message", "Toggled user successfully");
                    dir = "redirect:/admin/users";
                }
                else{
                    red.addFlashAttribute("error", "Sorry! Failed to toggle user");
                    dir = "redirect:/admin/users";
                }
                break;
        }

        return dir;
    }

    // profile/update @PreAuthorize("isAuthenticated()")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/update/{userid}")
    public String userProfileUpdate(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long userid,
            @ModelAttribute("profile") Profile profile, RedirectAttributes red) {
        red.addAttribute("profile", profileService.checkProfile(auth.getId()));

        profileService.update(userid, profile);
        auditService.record("update_profile", auth.getEmail() + " Updated the profile of user id=" + userid);
        red.addFlashAttribute("message", "Profile updated successfully");
        return "redirect:/admin/profile/{userid}";
    }

    // Update profile image
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image/update/{userid}")
    public String imageUpdate(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long userid,
            @RequestParam("image") MultipartFile file, RedirectAttributes red) {
        red.addAttribute("profile", profileService.checkProfile(auth.getId()));
        try {
            profileService.updateProfileImage(userid, file, red);
            auditService.record("update_image", auth.getEmail() + " Updated the profile image  of user id=" + userid);
            red.addFlashAttribute("message", "Image updated successfully");
        } catch (IOException e) {
            red.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/profile/{userid}";
    }

    // Delete profile image
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image/delete/{userid}")
    public String imageDelete(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long userid, RedirectAttributes red) {
        
        red.addAttribute("profile", profileService.checkProfile(auth.getId()));
        try {
            profileService.deleteProfileImage(userid, red);
            auditService.record("delete_image", auth.getEmail() + " Deleted the profile image of user id=" + userid);
            red.addFlashAttribute("message", "Image deleted successfully");
        } catch (IOException e) {
            red.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/profile/{userid}";
    }

    // Assign Remove roles
    @PostMapping("/{option}/{userId}/{roleId}")
    public String userRole(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long userId, @PathVariable String option,
            @PathVariable Long roleId, RedirectAttributes red) {
        red.addAttribute("profile", profileService.checkProfile(auth.getId()));

        switch (option) {
            case "assign-role":
                userService.assignRoleToUser(userId, roleId);
                auditService.record("update_role", auth.getEmail() + " Assigned user id=" + userId + " role id=" + roleId);
                red.addFlashAttribute("message", "Action successful");
                break;

            case "remove-role":
                userService.removeRoleFromUser(userId, roleId);
                auditService.record("update_role", auth.getEmail() + " Revoked role id=" + roleId + " from user id=" + userId);
                red.addFlashAttribute("message", "Action successful");
                break;

            default:
                red.addFlashAttribute("error", "Action failed");
                break;
        }

        return "redirect:/admin/manage/{userId}";
    }
}