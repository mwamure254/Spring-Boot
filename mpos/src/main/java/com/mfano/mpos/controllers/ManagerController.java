package com.mfano.mpos.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.models.Branch;
import com.mfano.mpos.services.security.ProfileService;
import com.mfano.mpos.services.security.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails auth, RedirectAttributes red) {
        red.addFlashAttribute("profile", profileService.checkProfile(auth.getId()));
        return "manager/index";
    }

    @GetMapping("/manager/users")
    public String storeUsers(Model model) {
        //Branch store = storeContext.getCurrentStore();
        //model.addAttribute("users", userService.findByStore(store));
        //model.addAttribute("store", store);
        return "manager/users";
    }
}
