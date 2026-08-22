package com.mfano.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
    // Error
    @GetMapping("/error")
    public String errorPage() {
        return "security/error";
    }

  @GetMapping("/author/dashboard")
  public String authorDashboard() {
    return "author/dashboard";
  }

  @GetMapping("/editor/dashboard")
  public String editorDashboard() {
    return "editor/dashboard";
  }

  @GetMapping("/user/dashboard")
  public String userDashboard() {
    return "user/dashboard";
  }
}
