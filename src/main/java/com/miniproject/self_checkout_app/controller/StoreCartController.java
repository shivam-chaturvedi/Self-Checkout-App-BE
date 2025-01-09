package com.miniproject.self_checkout_app.controller;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.miniproject.self_checkout_app.model.StoreCart;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.service.StoreCartService;
import com.miniproject.self_checkout_app.service.UserCartService;
import com.miniproject.self_checkout_app.service.UserService;
import com.miniproject.self_checkout_app.utils.QRCodeGenerator;

@RestController
public class StoreCartController {
	private final StoreCartService storeCartService;
	private final UserCartService userCartService;
	private final UserService userService;
	
	public StoreCartController(StoreCartService storeCartService,UserCartService userCartService, UserService userService) {
		this.storeCartService=storeCartService;
		this.userCartService = userCartService;
		this.userService = userService;
	}
	
	@GetMapping(path="/store-cart/qr/{id}")
	public ResponseEntity<?> getQrForStoreCart(@PathVariable("id") Long storeCartId){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			// Generate QR code for the specified product's ID, throw an exception if not
			// found
			StoreCart storeCart = storeCartService.getStoreCartById(storeCartId)
					.orElseThrow(() -> new RuntimeException("StoreCart not found with id: " + storeCartId));

			outputStream = QRCodeGenerator.generateBarcode(storeCart.getId().toString());

			// Set headers for file download
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=store_cart_qr_code_"+storeCart.getId()+".png");

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping(path="/admin/store-cart/create")
	public ResponseEntity<?> addNewCart(@RequestBody StoreCart storeCart) {
		Map<String, Object> res=new HashMap<String, Object>();
		try {
			StoreCart cart=storeCartService.createNewStoreCart(storeCart);
			res.put("success", true);
			res.put("storeCart", cart);
			return ResponseEntity.ok(res);
		}catch(Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
	
	@PostMapping(path="/store-cart/attach/{id}")
	public ResponseEntity<?> attachStoreCartWithUser(@PathVariable("id") Long storeCartId,@RequestBody User user){
		Map<String, Object> res=new HashMap<String, Object>();
		try {
			if(user.getEmail()==null) {
				throw new Exception("User Email is required!");
			}
			StoreCart cart=storeCartService.getStoreCartById(storeCartId).get();
			User u=userService.getUserFromUsername(user.getEmail());
			if(cart.getCurrentUser()==null || cart.getCurrentUser()==u) {
				if(cart.getCurrentUser()!=u) {
					cart=storeCartService.attachStoreCartWithUser(cart, u);					
				}
//				after attaching cart with user ,create a virtual user cart to store every product in that cart
				UserCart userCart=userCartService.getVirtualUserCart(cart);

				res.put("success", true);
				res.put("userCart", userCart);
				return ResponseEntity.ok(res);
			}
			
			else {
				throw new Exception("Store Cart With id = "+storeCartId+" already attached with another user!");
			}
		}catch(Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
	
	
	@PostMapping(path="/store-cart/detach/{id}")
	public ResponseEntity<?> detachStoreCartWithUser(@PathVariable("id") Long storeCartId){
		Map<String, Object> res=new HashMap<String, Object>();
		try {
			StoreCart cart=storeCartService.getStoreCartById(storeCartId).get();
			cart=storeCartService.detachStoreCartFromAnyUser(cart);
			res.put("success", true);
			res.put("storeCart", cart);
			return ResponseEntity.ok(res);
		}catch(Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
}
