package com.mfano.mpos.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.services.security.UserService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final UserService userService;

    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails auth, Model red) {
        userService.getPrices(auth,red);
        return "index";
    }
}
