package com.miniproject.self_checkout_app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.service.UserService;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/admin/get-all-users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/admin/add/user")
	public ResponseEntity<?> addAuthorizedUser(@RequestBody User user) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		try {
			User u = userService.signupNewUser(user);
			resMsg.put("success", u);
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}

	@PutMapping("/admin/update/user")
	public ResponseEntity<?> updateAuthorizedUser(@RequestBody User user) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		try {
			User u = userService.updateUser(user);
			resMsg.put("success", u);
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}

	@DeleteMapping("/admin/user/delete/{email}")
	public ResponseEntity<?> deleteUser(@PathVariable("email") String email) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		try {
			userService.removeUser(email);
			resMsg.put("success", "user deleted");
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}

	@GetMapping(path = "/user/{email}")
	public ResponseEntity<?> getUser(@PathVariable("email") String email) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		try {
			User u = userService.getUserFromUsername(email);
			resMsg.put("user", u);
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}
	

	@GetMapping(path = "/user/get-active-cart/{email}")
	public ResponseEntity<?> getActiveCartForUser(@PathVariable("email") String email) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		try {
			User u = userService.getUserFromUsername(email);
			UserCart cart= userService.getUserCartByUser(u);
			if(cart==null) {
				throw new Exception("No Active Cart Found For This User!");
			}
			resMsg.put("userCart", cart);
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}
	
}