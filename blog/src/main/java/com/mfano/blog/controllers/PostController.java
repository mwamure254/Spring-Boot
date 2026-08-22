package com.mfano.blog.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mfano.blog.config.CustomUserDetails;
import com.mfano.blog.dtos.PostDto;
import com.mfano.blog.models.Post;
import com.mfano.blog.services.CategoryService;
import com.mfano.blog.services.PostService;

import lombok.RequiredArgsConstructor;

@Controller 
@RequestMapping("/posts") 
@RequiredArgsConstructor
public class PostController {
    //private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/create")
    public String createForm(Model model){
        model.addAttribute("post", new PostDto());
        model.addAttribute("categories", categoryService.findAll());
        return "posts/create";
    }
  
}
