package com.mfano.mfano.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/login")
	public String login(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout() {
		return "login";
	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}

}
