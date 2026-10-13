package com.mfano.mpos.controllers;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.models.Branch;
import com.mfano.mpos.services.BranchService;
import com.mfano.mpos.services.security.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/branches")
public class BranchController {
        private final BranchService branchService;
        private final UserService userService;

        public BranchController(BranchService branchService, UserService userService) {
                this.branchService = branchService;
                this.userService = userService;
        }

        @GetMapping
        public String index(@AuthenticationPrincipal CustomUserDetails auth, Model model) {
                userService.getPrices(auth,model);
                model.addAttribute("branches", branchService.findAll());
                model.addAttribute("branch", new Branch());

                return "manager/branches";
        }

        @PostMapping("/save")
        public String save(
                        @ModelAttribute Branch branch,
                        RedirectAttributes redirectAttributes) {

                if (branchService.findCode(branch.getCode())) {
                        redirectAttributes.addFlashAttribute(
                                        "error", "Branch code already exists.");
                        return "redirect:/branches";
                }

                if (branchService.findCode(branch.getName())) {
                        redirectAttributes.addFlashAttribute(
                                        "error", "Branch name already exists.");
                        return "redirect:/branches";
                }

                branchService.createBranch(branch);

                redirectAttributes.addFlashAttribute(
                                "message", "Branch created successfully.");

                return "redirect:/branches";
        }

        @PostMapping("/update/{id}")
        public String update(
                        @PathVariable Long id,
                        @ModelAttribute Branch updatedBranch,
                        RedirectAttributes redirectAttributes) {

                Branch branch = branchService.findById(id);
                if (branch == null) {
                        redirectAttributes.addFlashAttribute(
                                        "error", "Branch not found: " + id);
                }

                branch.setName(updatedBranch.getName());
                branch.setCode(updatedBranch.getCode());
                branch.setLocation(updatedBranch.getLocation());
                branch.setPhone(updatedBranch.getPhone());
                branch.setEmail(updatedBranch.getEmail());

                branchService.createBranch(branch);

                redirectAttributes.addFlashAttribute(
                                "message", "Branch updated successfully.");

                return "redirect:/branches";
        }

        @PostMapping("/toggle/{id}")
        public String toggle(
                        @PathVariable Long id,
                        RedirectAttributes redirectAttributes) {

                Branch branch = branchService.findById(id);
                if (branch == null) {
                        redirectAttributes.addFlashAttribute(
                                        "error", "Branch not found: " + id);
                }

                branch.setActive(!branch.isActive());
                branchService.createBranch(branch);

                redirectAttributes.addFlashAttribute(
                                "message", "Branch status updated successfully.");

                return "redirect:/branches";
        }

        @PostMapping("/delete/{id}")
        public String delete(
                        @PathVariable Long id,
                        RedirectAttributes redirectAttributes) {

                branchService.deleteBranch(id);

                redirectAttributes.addFlashAttribute(
                                "message", "Branch deleted successfully.");

                return "redirect:/branches";
        }
}