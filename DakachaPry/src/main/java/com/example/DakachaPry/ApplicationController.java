package com.example.DakachaPry;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.DakachaPry.security.models.UserPrincipal;

@Controller
public class ApplicationController {

    @GetMapping("/index")
    public String goHome(Model model, UserPrincipal user) {
        user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("signed", user);
        return "index";
    }

    @GetMapping("hr")
    public String hr() {
        return "/hr/index";
    }

    @GetMapping("fleet")
    public String fleet() {
        return "/fleet/index";
    }

    @GetMapping("accounts")
    public String accounts() {
        return "/accounts/index";
    }

    @GetMapping("payroll")
    public String payroll() {
        return "/payroll/index";
    }

    @GetMapping("helpdesk")
    public String helpdesk() {
        return "/helpdesk/index";
    }

    @GetMapping("parameters")
    public String parameters() {
        return "/parameters/index";
    }

    @GetMapping("reports")
    public String reports() {
        return "/reports/index";
    }

    @GetMapping("security")
    public String security() {
        return "/security/index";
    }
}
