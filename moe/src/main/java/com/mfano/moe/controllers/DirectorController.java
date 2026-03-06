package com.mfano.moe.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.mfano.moe.models.Institution;
import com.mfano.moe.models.SBoard;
import com.mfano.moe.security.service.AuditService;
import com.mfano.moe.security.service.ProfileService;
import com.mfano.moe.security.service.RoleService;
import com.mfano.moe.security.service.UserService;
import com.mfano.moe.services.InstitutionService;
import com.mfano.moe.services.ICategoryService;
import com.mfano.moe.services.ILevelService;
import com.mfano.moe.services.IStatusService;
import com.mfano.moe.services.SStatusService;
import com.mfano.moe.services.SBoardService;
import com.mfano.moe.services.UStatusService;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/director")
@RequiredArgsConstructor
@EnableMethodSecurity

@PreAuthorize("hasRole('DIRECTOR')")
public class DirectorController {

    private final RoleService roleService;
    private final InstitutionService institutionService;
    private final SBoardService serviceBoardService;
    private final AuditService auditService;
    private final ProfileService profileService;
    private final UserService userService;

    // statuses
    private final ICategoryService iCategoryService;
    private final IStatusService iStatusService;
    private final SStatusService sStatusService;
    private final UStatusService uStatusService;
    private final ILevelService iLevelService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {

        userService.redirectUser(auth, model);
        List<Institution> schools = institutionService.findAll();
        model.addAttribute("institutions", schools);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("profiles", profileService.findAll());
        model.addAttribute("is", iStatusService.findAll());
        model.addAttribute("ss", sStatusService.findAll());
        model.addAttribute("us", uStatusService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("levels", iLevelService.findAll());
        model.addAttribute("statuses", iStatusService.findAll());
        model.addAttribute("categories", iCategoryService.findAll());
        model.addAttribute("logs", auditService.findAll());

        model.addAttribute("message", "login successsful");
        return "scde/dashboard";
    }

    // Create a new school
    @PostMapping("/create/school")
    public String creatSchool(Institution ins, Model model) {
        try {
            institutionService.save(ins);
            model.addAttribute("message", "School created successfully");
            auditService.record("create_school", "scde", "Created school id= " + ins.getId());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/director/dashboard";
    }

    // Create a new school
    @PostMapping("/assign/school")
    public String assignSchool(SBoard board, Model model) {
        try {
            serviceBoardService.save(board);
            model.addAttribute("message", "service created successfully");
            auditService.record("create_service", "scde", "Created service id= " + board.getId());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/director/schools";
    }

    // Update school
    @PostMapping("/update/school/{id}")
    public String updateSchool(@ModelAttribute("institution") Institution ins, @PathVariable Long id, Model model) {
        try {
            institutionService.save(ins);
            model.addAttribute("message", "School updated successfully");
            auditService.record("update_school", "scde", "Updated school id= " + ins.getId());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/director/dashboard";
    }

    // Get school
    @GetMapping("/schools")
    public String getSchools(Authentication auth, Model model) {
        userService.redirectUser(auth, model);
        List<Institution> schools = institutionService.findAll();

        model.addAttribute("institutions", schools);
        model.addAttribute("profiles", profileService.findAll());
        model.addAttribute("is", iStatusService.findAll());
        model.addAttribute("ss", sStatusService.findAll());
        model.addAttribute("us", uStatusService.findAll());
        model.addAttribute("levels", iLevelService.findAll());
        model.addAttribute("statuses", iStatusService.findAll());
        model.addAttribute("categories", iCategoryService.findAll());

        return "scde/schools";
    }
}
