package com.mfano.mpos.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mfano.mpos.services.security.AuditService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthHandler implements AuthenticationSuccessHandler,
        AuthenticationFailureHandler {
    private final AuditService auditService;
    String message = "Invalid username or password";

    private String msg(String ms){
               return "/login?error=true&message=" +
                            URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails u)) {
            response.sendRedirect(
                msg(message));
        return;
        }
        
        // Extract roles
        Set<String> roles = u.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // log activity info
        if (authentication != null &&
                authentication.getPrincipal() instanceof CustomUserDetails user) {

            auditService.record(
                    "user_login",
                    "user",
                    "User " + user.getUsername() + " logged in successfully.");

        }
        else {
            message = "Invalid user, check your credentials and try again.";
            response.sendRedirect(
                msg(message));
            }

        if (roles.isEmpty()) {
            message = "Please contact the system admin for mapping.";
            response.sendRedirect(
                msg(message));
        }
        else if(!u.isEnabled()){ 
            message = "Contact the system admin for account verification.";
            response.sendRedirect(
                msg(message));
            }
        else if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");

        } else if (roles.contains("ROLE_MANAGER")) {
            response.sendRedirect("/manager/dashboard");

        } else if (roles.contains("ROLE_INVENTORY")) {
            response.sendRedirect("/inventory/dashboard");

        }else if (roles.contains("ROLE_CASHIER")) {
            response.sendRedirect("/cashier/dashboard");

        } else {
            response.sendRedirect("/guest/dashboard");
        }
    }
    
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        if (exception instanceof DisabledException) {
            message = "Your account is disabled";
        } else if (exception instanceof LockedException) {
            message = "Your account is locked";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "Your password has expired";
        }

        response.sendRedirect(
                msg(message));
    }
}
