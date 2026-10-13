package com.mfano.mpos.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.services.security.UserService;

@Controller
@RequestMapping("/guest")
public class GuestController {
    private final UserService userService;
    
    public GuestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{option}")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails auth, Model model, @PathVariable String option) {
        userService.getPrices(auth,model);
        String dir = "";
        switch (option) {
            case "dashboard":
                dir = "index";
                break;
        
            default:
                break;
        }
        return dir;
    }
}
