package com.mfano.moe.security;
//imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//annotations
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Customize your security configuration
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/index", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/login", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/logout", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/error", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login").permitAll().defaultSuccessUrl("/index"))
                .exceptionHandling(handling -> handling.accessDeniedPage("/error"))
                .logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                        .permitAll());
        return http.build();
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

}
