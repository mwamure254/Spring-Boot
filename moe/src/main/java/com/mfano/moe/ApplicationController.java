package com.mfano.moe;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mfano.moe.security.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
  private final UserService userService;

  @GetMapping("/")
  public String home() {
    return "redirect:/dashboard";
  }

  // Error
  @GetMapping("/error")
  public String errorPage() {
    return "security/error";
  }

  @GetMapping("/hoi/dashboard")
  public String hoiDashboard() {
    return "hoi/dashboard";
  }

  @GetMapping("/user/dashboard")
  public String userDashboard() {
    return "user/dashboard";
  }

  @GetMapping("/settings")
  public String settings(Authentication auth, HttpServletRequest request, Model model) {
    userService.redirectUser(auth, model);
    model.addAttribute("requestURI", request.getRequestURI());
    return "security/settings";
  }

  @GetMapping("/contact")
  public String contact(Authentication auth, HttpServletRequest request, Model model) {
    userService.redirectUser(auth, model);
    model.addAttribute("requestURI", request.getRequestURI());
    return "security/contact";
  }

  @GetMapping("/faq")
  public String faq(Authentication auth, HttpServletRequest request, Model model) {
    userService.redirectUser(auth, model);
    model.addAttribute("requestURI", request.getRequestURI());
    return "security/faq";
  }

  @GetMapping("/help")
  public String help(Authentication auth, HttpServletRequest request, Model model) {
    userService.redirectUser(auth, model);
    model.addAttribute("requestURI", request.getRequestURI());
    return "security/help";
  }

}
