package com.AuthenticationAndAuthorization.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {
	@Bean
	public BCryptPasswordEncoder BCrypt() {
		return new BCryptPasswordEncoder(12);
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	            		.requestMatchers("/register", "/h2-console").permitAll()
	                    .anyRequest().authenticated()
	            )
	            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
	            .formLogin(form -> form.permitAll())
	            .logout(logout -> logout.permitAll());

	    return http.build();
	}

}
