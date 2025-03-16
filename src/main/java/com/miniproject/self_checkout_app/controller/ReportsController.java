package com.miniproject.self_checkout_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.miniproject.self_checkout_app.service.UserCartService;

@RestController
public class ReportsController {
	private final UserCartService userCartService;
	
	public ReportsController(UserCartService userCartService) {
		this.userCartService=userCartService;
	}
	
	@GetMapping(path="/reports/get-all-sales-data")
	public ResponseEntity<?> getAllSales(){
		Map<String,Object> res=new HashMap<>();
		try {
			res.put("Purchased Items", userCartService.getAllSoldProducts());
			return ResponseEntity.ok(res);
		}
		catch(Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
		
	}
}
