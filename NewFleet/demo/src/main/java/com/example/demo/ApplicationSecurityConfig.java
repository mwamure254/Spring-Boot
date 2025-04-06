package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private UserDetailsService userDetailsService;

	@SuppressWarnings({})
	@Override
	public void init(HttpSecurity http) throws Exception {
		// Customize your security configuration
		http.authorizeHttpRequests(requests -> requests
				.requestMatchers("/login", "/resources/**", "/css/**", "/scss/**", "/vendor/**", "/img/**").permitAll()
				.requestMatchers("/register", "/resources/**", "/css/**", "/scss/**", "/vendor/**", "/img/**", "/js/**")
				.permitAll().requestMatchers("/users/addNew").permitAll().requestMatchers("/h2-console").permitAll()
				.requestMatchers("/security/user/Edit/**").hasRole("ADMIN")
				.anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/login").permitAll().defaultSuccessUrl("/index"))
				.exceptionHandling(handling -> handling.accessDeniedPage("/accessDenied"))
				.logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
						.permitAll());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Additional configuration if needed
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return bCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(userDetailsService);

		provider.setPasswordEncoder(bCryptPasswordEncoder());
		return provider;
	}
}
