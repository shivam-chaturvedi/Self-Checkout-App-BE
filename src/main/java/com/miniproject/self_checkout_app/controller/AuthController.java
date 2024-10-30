package com.miniproject.self_checkout_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.service.UserService;

@Controller
public class AuthController {
	
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute User user) {
	    try {
	        userService.signupNewUser(user);
	        return "redirect:/login";  // Redirect to the login page
	    } catch (Exception e) {
	        return "redirect:/signup?error=" + e.getMessage();  // Redirect back to signup with an error message
	    }
	}
	
	@PostMapping("/admin/add")
	public String addUser(@ModelAttribute User user) {
		userService.signupNewUser(user);
		return "redirect:/admin";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	
	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}
	
	

}
