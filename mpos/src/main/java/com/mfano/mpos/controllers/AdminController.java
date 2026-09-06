package com.mfano.mpos.controllers;

import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.dtos.UserDto;
import com.mfano.mpos.models.security.Role;
import com.mfano.mpos.services.BranchService;
import com.mfano.mpos.services.security.AuditService;
import com.mfano.mpos.services.security.ProfileService;
import com.mfano.mpos.services.security.RoleService;
import com.mfano.mpos.services.security.UserService;

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

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails auth, RedirectAttributes red) {
        red.addAttribute("users", userService.findAll());
        red.addAttribute("stores", storeService.findAll());
        red.addAttribute("roles", roleService.findAll());
        red.addAttribute("audits", auditService.findAll());
        red.addFlashAttribute("profile", profileService.checkProfile(auth.getId()));
        return "admin/index";
    }

    @GetMapping("/stores")
    public String stores(@AuthenticationPrincipal CustomUserDetails auth, RedirectAttributes red) {
        red.addFlashAttribute("profile", profileService.checkProfile(auth.getId()));
        red.addAttribute("stores", storeService.findAll());
        return "admin/stores";
    }

    @GetMapping("/users")
    public String users(@AuthenticationPrincipal CustomUserDetails auth,
            Model red) {
        red.addAttribute("profile", profileService.checkProfile(auth.getId()));
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
        return "admin/users";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute UserDto userDto, RedirectAttributes red) {
        try {
            userService.registerUser(userDto.getEmail(), userDto.getPassword(), userDto.getBranch(),
                    userDto.getRoles());
            auditService.record("CREATE_USER", "admin", "Created user: " + userDto.getEmail());
            red.addFlashAttribute("message", "User created successfully!");
        } catch (Exception e) {
            red.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
}