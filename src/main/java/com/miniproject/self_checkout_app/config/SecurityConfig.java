package com.miniproject.self_checkout_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.miniproject.self_checkout_app.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserService userDetailService;
	
	public SecurityConfig(UserService userService) {
		this.userDetailService = userService;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> 
        csrf.ignoringRequestMatchers("/admin/product/delete/**")) // Exempt CSRF for delete product
        .authorizeHttpRequests(auth -> 
            auth.requestMatchers("/signup").permitAll()
            .requestMatchers("/css/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login").permitAll())
        .logout(logout -> 
            logout.logoutUrl("/logout")
                  .logoutSuccessUrl("/login?logout") // Redirect to login with a logout indicator
                  .invalidateHttpSession(true) // Invalidate session
                  .clearAuthentication(true) // Clear authentication info
                  .deleteCookies("JSESSIONID") // Optional: delete session cookie
        );

		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
		
		authenticationManagerBuilder.userDetailsService(userDetailService)
            .passwordEncoder(passwordEncoder());
		
		return authenticationManagerBuilder.build();
	}
}
