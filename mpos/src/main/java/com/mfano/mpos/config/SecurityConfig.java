package com.mfano.mpos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mfano.mpos.services.security.CustomDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
        private final CustomDetailService customDetailService;
        private final PasswordEncoder passwordEncoder;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, AuthHandler authHandler) throws Exception {
                http.csrf(csrf -> csrf.disable());
                http.authorizeHttpRequests(auth -> auth

                                .requestMatchers("/**/dashboard").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/manager/**").hasRole("MANAGER")
                                .requestMatchers("/cashier/**").hasRole("CASHIER")
                                .requestMatchers("/procurement/**").hasRole("PROCUREMENT")
                                .requestMatchers("/", "/register", "/login", "/verify", "/forgot", "/reset-password",
                                                "/resend", "/error", "/profile", "/css/**", "/js/**", "/vendor/**",
                                                "/image/**")
                                .permitAll()
                                .anyRequest().authenticated())

                                .formLogin(form -> form
                                                .loginPage("/login")
                                                // .loginProcessingUrl("/login")
                                                .successHandler(authHandler)
                                                .failureHandler(authHandler)
                                                .defaultSuccessUrl("/", true)
                                                // .successForwardUrl("/dashboard")
                                                .permitAll())

                                .exceptionHandling(handling -> handling
                                                .accessDeniedPage("/error"))

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

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customDetailService);

                provider.setPasswordEncoder(passwordEncoder);
                return provider;
        }

}
