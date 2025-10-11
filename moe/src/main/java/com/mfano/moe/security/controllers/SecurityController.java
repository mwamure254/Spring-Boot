package com.mfano.moe.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String loginPage() {
        return "security/login";
    }

    @GetMapping("/register")
    public String register() {
        return "security/register";
    }

    @GetMapping("/error")
    public String blank() {
        return "error";
    }
    
    @GetMapping("/forgotPassword")
    public String forgotPassword(){
       return "security/forgotPassword";
    }

}
