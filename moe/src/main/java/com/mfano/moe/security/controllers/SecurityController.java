package com.mfano.moe.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class SecurityController {

   @GetMapping("/login")
	public String login(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "security/login";
		}
		return "redirect:/";
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
