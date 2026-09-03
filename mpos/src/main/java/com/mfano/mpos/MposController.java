package com.mfano.mpos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MposController {
     // Error
    @GetMapping("/error")
    public String errorPage() {
        return "security/error";
    }

}
