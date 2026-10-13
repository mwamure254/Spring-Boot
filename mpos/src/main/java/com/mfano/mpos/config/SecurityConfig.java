package com.mfano.mpos.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mfano.mpos.services.security.CustomDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
        private final CustomDetailService customDetailService;
        private final PasswordEncoder passwordEncoder;

        public SecurityConfig(CustomDetailService customDetailService, PasswordEncoder passwordEncoder) {
                this.customDetailService = customDetailService;
                this.passwordEncoder = passwordEncoder;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, AuthHandler authHandler) throws Exception {
                http.csrf(csrf -> csrf.disable());
                http.authorizeHttpRequests(auth -> auth

                                .requestMatchers("/**/dashboard").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/manager/**").hasRole("MANAGER")
                                .requestMatchers("/cashier/**").hasRole("CASHIER")
                                .requestMatchers("/inventory/**").hasRole("PROCUREMENT")
                                .requestMatchers("/", "/register", "/login", "/verify", "/forgot", "/reset-password",
                                                "/resend", "/error/403", "/profile", "/css/**", "/js/**", "/vendor/**",
                                                "/image/**")
                                .permitAll()
                                .anyRequest().authenticated())

                                .formLogin(form -> form
                                                .loginPage("/login")
                                                // .loginProcessingUrl("/login")
                                                .successHandler(authHandler)
                                                .failureHandler(authHandler)
                                                // .defaultSuccessUrl("/", true)
                                                // .successForwardUrl("/dashboard")
                                                .permitAll())

                                .exceptionHandling(handling -> handling
                                                .accessDeniedPage("/error/403"))

                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .addLogoutHandler(authHandler)
                                                .invalidateHttpSession(true)
                                                .clearAuthentication(true)
                                                .deleteCookies("JSESSIONID")
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())

                                .sessionManagement(management -> management
                                                .maximumSessions(1)

                                                .expiredUrl("/login?expired=true"));

                return http.build();
        }

      // =====================================================
    // AUTHENTICATION PROVIDER
    // =====================================================
    @Bean
    AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customDetailService);

        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // =====================================================
    // AUTHENTICATION MANAGER
    // =====================================================
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    // IMAGE ROUTE
    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path profilePath = Paths.get(uploadDir, "products")
                .toAbsolutePath()
                .normalize();

        Path documentPath = Paths.get(uploadDir, "profiles")
                .toAbsolutePath()
                .normalize();

        registry.addResourceHandler("/profiles/**", "/products/**")
                .addResourceLocations(profilePath.toUri().toString(), documentPath.toUri().toString());
    }
}
