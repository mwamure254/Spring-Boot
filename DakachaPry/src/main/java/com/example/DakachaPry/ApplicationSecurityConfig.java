package com.example.DakachaPry;

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
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.security.web.session.HttpSessionEventPublisher;

//import com.example.DakachaPry.services.MyUserDetailsService;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Customize your security configuration
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/login", "/resources/**", "/css/**", "/fonts/**", "/img/**").permitAll()
                .requestMatchers("/register", "/resources/**", "/css/**", "/fonts/**", "/img/**", "/js/**")
                .permitAll()
                .requestMatchers("/error", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/forgetPassword", "/resources/**", "/css/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/users/addNew").permitAll()
                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login").permitAll().defaultSuccessUrl("/index"))
                .exceptionHandling(handling -> handling.accessDeniedPage("/error"))
                .logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                        .permitAll())
                .sessionManagement(management -> management
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true"));;
        return http.build();
    }
    
    @Bean
	HttpSessionEventPublisher httpSessionEventPublisher() {
	return new HttpSessionEventPublisher();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
