package com.mfano.mpos.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.services.security.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cashier")
@RequiredArgsConstructor
public class CashierController {
    private final UserService userService;
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails auth, RedirectAttributes red) {
         userService.redirectUser(auth, red);
        return "cashier/index";
    }
}
