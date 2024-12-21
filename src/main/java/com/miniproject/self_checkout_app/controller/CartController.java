package com.miniproject.self_checkout_app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.service.CartService;
import com.miniproject.self_checkout_app.service.ProductService; 
import com.miniproject.self_checkout_app.service.UserService;

@Controller
@RequestMapping(path = "/cart")
public class CartController {
	private CartService cartService;
	private ProductService productService;
	private UserService userService;

	public CartController(CartService cartService, ProductService productService,UserService userService) {
		this.cartService = cartService;
		this.userService=userService;
		this.productService = productService;
	}

	@PostMapping(path = "/add-item/{userEmail}")
	public ResponseEntity<?> addItem(@RequestBody CartItem item,@PathVariable("userEmail") String userEmail) {
		Map<String, Object> res = new HashMap<String, Object>();
		Optional<Product> p = null;
		CartItem optionalItem = null;

		try {
			if (item.getProductId() == null) {
				res.put("error", "Cart Item Id is Required!");
				return ResponseEntity.badRequest().body(res);
			}
			
			p = productService.getProduct(item.getProductId());
			
			if (p.isEmpty()) {
				throw new Exception("Product With " + item.getProductId() + " Id Not Found !");
			}
			
			User user=userService.getUserFromUsername(userEmail);
			optionalItem = cartService.getCartItemByProductId(p.get().getId());
			if (optionalItem==null || !optionalItem.isCurrentCartItem()) {
				item.setQuantity(1L); // if new cart item then set quantity to 1
				item.setAmount(p.get().getPrice());
				
			} else {
				item=optionalItem;
				item.setQuantity(item.getQuantity() + 1);
				item.setAmount(item.getQuantity() * p.get().getPrice());
			}
			item.setUser(user);
			if(item.getQuantity()>p.get().getQuantity()) {
				throw new Exception("Product "+p.get().getName()+" Quantity Not Available!");
			}
			item=cartService.addItemToCart(item);
			res.put("cartItemId", item.getId());
			res.put("product", p);
			res.put("success", true);
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
	
	@DeleteMapping(path="/remove-item/{cartItemId}")
	public ResponseEntity<?> removeItemFromCart(@PathVariable("cartItemId") Long cartItemId){
		Map<String, Object> res=new HashMap<String, Object>();
		try {
			CartItem cartItem=cartService.getCartItemById(cartItemId).get();
			Product p=productService.getProduct(cartItem.getProductId()).get();
			if(cartItem.getQuantity()>1) {
//				update quantity and amount
				cartItem.setQuantity(cartItem.getQuantity()-1);
				cartItem.setAmount(cartItem.getQuantity()*p.getPrice());
				cartService.addItemToCart(cartItem);
			}
			else {
				cartService.deleteCartItem(cartItemId);				
			}
			res.put("success", "Cart Item Deleted !");
			return ResponseEntity.ok(res);
		}catch(Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
	
	
	@PostMapping(path = "/add-item-by-rfid-tag/{rfidtag}")
	public ResponseEntity<?> addItemByRfidTag(@PathVariable("rfidtag") String rfidtag) {
		Map<String, Object> res = new HashMap<String, Object>();
		Optional<Product> p = null;
		CartItem optionalItem = null;
		CartItem item=new CartItem();
		try {
			
			p = productService.getProductByRfidTag(rfidtag.trim());
			if (p.isEmpty()) {
				throw new Exception("Product With " + rfidtag + " Not Found !");
			}

			optionalItem = cartService.getCartItemByProductId(p.get().getId());
			if (optionalItem==null) {
				item.setQuantity(1L); // if new cart item then set quantity to 1
				item.setAmount(p.get().getPrice());
			} else {
				item=optionalItem;
				item.setQuantity(item.getQuantity() + 1);
				item.setAmount(item.getQuantity() * p.get().getPrice());
			}
			res.put("cart", cartService.addItemToCart(item));
			res.put("success", true);
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
	
	@GetMapping(path = "/get-all/{userEmail}")
	public ResponseEntity<?> getCartByUserEmail(@PathVariable("userEmail") String email) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			User user = userService.getUserFromUsername(email);
			List<com.miniproject.self_checkout_app.dto.CartItem> cartDTOList=new ArrayList<com.miniproject.self_checkout_app.dto.CartItem>();
			List<CartItem> cart = userService.getCartByUser(user);
			for(CartItem item:cart) {
				com.miniproject.self_checkout_app.dto.CartItem c=new com.miniproject.self_checkout_app.dto.CartItem();
				c.setAmount(item.getAmount());
				c.setCartId(item.getId());
				c.setProduct(productService.getProduct(item.getProductId()).get());
				c.setQuantity(item.getQuantity());
				cartDTOList.add(c);
			}
			res.put("success", true);
			res.put("cart", cartDTOList);
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}


	
}  
