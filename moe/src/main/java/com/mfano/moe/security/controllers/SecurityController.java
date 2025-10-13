package com.mfano.moe.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String loginPage() {
        return "security/login";
    }

	@GetMapping("/logout")
	public String logout() {
		return "login";
	}

    @GetMapping("security/register")
    public String register() {
        return "security/register";
    }

    @GetMapping("/error")
    public String blank() {
        return "security/error";
    }
    
    @GetMapping("security/reset")
    public String forgotPassword(){
       return "security/reset";
    }

}
