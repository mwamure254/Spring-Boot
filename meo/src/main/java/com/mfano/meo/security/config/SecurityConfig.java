package com.mfano.meo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Customize your security configuration
    	//http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/home", "/resources/**", "/css/**", "/fonts/**", "/js/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/users", "/resources/**", "/css/**", "/fonts/**", "/img/**", "/js/**", "/scss/**", "/vendor/**")
                .permitAll()
                .requestMatchers("/error404","/css/**", "/vendor/**").permitAll()
                .requestMatchers("/login", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/profile", "/resources/**", "/css/**", "/scss/**", "/fonts/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .requestMatchers("/settings", "/resources/**", "/css/**", "/scss/**","/fonts/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .requestMatchers("/contact", "/resources/**", "/css/**", "/scss/**", "/fonts/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .requestMatchers("/faq", "/resources/**", "/css/**", "/scss/**","/fonts/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .requestMatchers("/service-board", "/resources/**", "/css/**", "/scss/**","/fonts/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .requestMatchers("/schools", "/resources/**", "/css/**", "/scss/**","/fonts/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .requestMatchers("/reports", "/resources/**", "/css/**", "/scss/**","/fonts/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .requestMatchers("/editables", "/resources/**", "/css/**", "/scss/**","/fonts/**", "/img/**", "/js/**", "/vendor/**").permitAll()
                .requestMatchers("/logout").permitAll()
                .anyRequest().authenticated())
        
                .formLogin(login -> login.loginPage("/home").permitAll().defaultSuccessUrl("/home"))
                .exceptionHandling(handling -> handling.accessDeniedPage("/error404"))
                
                .logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                        .permitAll())
                
                .sessionManagement(management -> management
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true"));
        
        return http.build();
    }
    
}