package com.mfano.meo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Customize your security configuration
    	http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/home", "/resources/**", "/css/**", "/fonts/**", "/img/**").permitAll()
                .requestMatchers("/users", "/resources/**", "/css/**", "/fonts/**", "/img/**", "/js/**")
                .permitAll()
                .requestMatchers("/users/**", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated())
        
                .formLogin(login -> login.loginPage("/home").permitAll().defaultSuccessUrl("/home"))
                .exceptionHandling(handling -> handling.accessDeniedPage("/"))
                
                .logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/")).logoutSuccessUrl("/")
                        .permitAll())
                
                .sessionManagement(management -> management
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true"));
        
        return http.build();
    }
	
}