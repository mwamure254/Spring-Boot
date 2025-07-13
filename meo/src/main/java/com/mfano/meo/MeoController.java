package com.mfano.meo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeoController {
	
	@GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/logout")
    public String out() {
        return "index";
    }
	
    @GetMapping("/error404")
    public String error404() {
        return "security/error404";
    }

    @GetMapping("/profile")
    public String profile() {
        return "security/profile";
    }

    @GetMapping("/settings")
    public String settings() {
        return "security/settings";
    }

    @GetMapping("/contact")
    public String contact() {
        return "security/contact";
    }

    @GetMapping("/faq")
    public String FAQ() {
        return "security/faq";
    }
}