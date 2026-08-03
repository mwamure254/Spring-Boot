package com.mfano.moe.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.mfano.moe.security.service.AuditService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogHandler implements LogoutHandler {

    private final AuditService auditService;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        if (authentication != null &&
            authentication.getPrincipal() instanceof CustomUserDetails user) {

            auditService.record(
                    "user_logout",
                    "user",
                    "User " + user.getUsername() + " logged out"
            );
        }
    }
}
