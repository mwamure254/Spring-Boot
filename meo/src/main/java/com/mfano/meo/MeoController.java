package com.mfano.meo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MeoController {

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "security/login";
    }


    @GetMapping("/logout")
    public String out() {
        return "login";
    }

    @GetMapping("/error404")
    public String error404() {
        return "security/error404";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "security/profile";
    }

    @GetMapping("/settings")
    public String settings(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "security/settings";
    }

    @GetMapping("/contact")
    public String contact(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "security/contact";
    }

    @GetMapping("/faq")
    public String FAQ(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "security/faq";
    }

    @GetMapping("/service-board")
    public String serviceBoard(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "security/service-board";
    }

    @GetMapping("/schools")
    public String schools(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "security/schools";
    }

    @GetMapping("/reports")
    public String reports(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "security/reports";
    }

    @GetMapping("/editables")
    public String editables(HttpServletRequest request, Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "security/editables";
    }

}