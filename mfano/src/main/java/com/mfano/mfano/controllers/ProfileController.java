package com.mfano.mfano.controllers;

import java.security.Principal;

import com.mfano.mfano.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/profiles")
    public String profile(Model model, Principal principal) {
        String un = principal.getName();
        model.addAttribute("employee", employeeService.findByUsername(un));
        return "profile";
    }
}
