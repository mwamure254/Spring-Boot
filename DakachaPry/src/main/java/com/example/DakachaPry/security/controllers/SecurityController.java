package com.example.DakachaPry.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
