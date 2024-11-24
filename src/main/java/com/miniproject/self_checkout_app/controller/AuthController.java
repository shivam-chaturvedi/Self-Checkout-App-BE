package com.miniproject.self_checkout_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.service.UserService;
import com.miniproject.self_checkout_app.utils.JwtUtil;

@RestController
public class AuthController {

	private final UserService userService;
	private final JwtUtil jwtUtil;

	public AuthController(UserService userService,JwtUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil=jwtUtil;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		Map<String, Object> responseMsg = new HashMap<String, Object>();
		try {
			// Call service to signup new user
			userService.signupNewUser(user);

			// Return response with CREATED status (201)
			responseMsg.put("success", user);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseMsg);
		} catch (Exception e) {
			// Return BAD_REQUEST status (400) with error message
			responseMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(responseMsg);
		}
	}


	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		
		if (userService.authenticateUser(user)) {
			String token = jwtUtil.generateToken(user.getEmail());
			User u=userService.getUserFromUsername(user.getEmail());
			resMsg.put("success", token);
			resMsg.put("user", u);
			return ResponseEntity.ok(resMsg);
		}
		resMsg.put("error", "Username or Password is invalid !");
		return ResponseEntity.badRequest().body(resMsg);

	}
	
	@GetMapping(path="/verify-token")
	public ResponseEntity<?> verifyJwtToken(@RequestParam("token") String token){
		Map<String, Object> resMsg = new HashMap<>();
	    if (token == null || token.isEmpty()) {;
	        resMsg.put("error", "Token is required");
	        return ResponseEntity.badRequest().body(resMsg);
	    }
	    if (jwtUtil.validateToken(token)) {
	    	System.out.println("verify token ");
	    	boolean isAdmin=userService.isUserAdmin(jwtUtil.extractUsername(token));
	    	String uname=jwtUtil.extractUsername(token);
	    	User user=userService.getUserFromUsername(uname);
	        resMsg.put("success", "Token is valid !");
	        resMsg.put("isAdmin", isAdmin);
	        resMsg.put("user", user);
	        return ResponseEntity.ok(resMsg);
	    } else {
	        resMsg.put("error", "Token invalid!");
	        return ResponseEntity.badRequest().body(resMsg);
	    }
	}

}
