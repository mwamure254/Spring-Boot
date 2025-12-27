package com.mfano.moe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

  @GetMapping("/")
  public String home() {
    return "redirect:/dashboard";
  }

  // Error
  @GetMapping("/error-page")
  public String errorPage() {
    return "security/error";
  }

  @GetMapping("/director/dashboard")
  public String directorDashboard() {
    return "director/dashboard";
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
}
