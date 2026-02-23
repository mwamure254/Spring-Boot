package com.mfano.moe.controllers;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/director")
@RequiredArgsConstructor
@EnableMethodSecurity
public class DirectorController {

}
