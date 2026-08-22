package com.mfano.blog.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfano.blog.services.PostService;
import com.mfano.blog.services.security.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    //private final PostService postService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

}
