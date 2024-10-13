package com.miniproject.self_checkout_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		System.out.println("in /");
		return "home";
		
	}
}