package com.miniproject.self_checkout_app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.model.StoreCart;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.service.CartItemService;
import com.miniproject.self_checkout_app.service.UserCartService;
import com.miniproject.self_checkout_app.service.ProductService;
import com.miniproject.self_checkout_app.service.StoreCartService;
import com.miniproject.self_checkout_app.service.UserService;

@RestController
public class UserCartController {
	private CartItemService cartItemService;
	private UserCartService cartService;
	private ProductService productService;
	private UserService userService;
	private StoreCartService storeCartService;

	public UserCartController(CartItemService cartItemService, ProductService productService, UserService userService,
			UserCartService cartService,StoreCartService storeCartService) {
		this.cartService = cartService;
		this.userService = userService;
		this.storeCartService=storeCartService;
		this.productService = productService;
		this.cartItemService = cartItemService;

	}
	
	@PostMapping(path = "/user-cart/add-item")
	public ResponseEntity<?> addItem(@RequestParam Long productId, @RequestParam Long cartId) {
		Map<String, Object> res = new HashMap<String, Object>();
		Optional<Product> p = null;
		try {
			if (cartId == null) {
				throw new Exception("Cart Item Id is Required!");
			}

			p = productService.getProduct(productId);

			if (p.isEmpty()) {
				throw new Exception("Product With " + productId + " Id Not Found !");
			}
			CartItem item=cartService.addNewItemToUserCart(cartId, p.get());

			res.put("cartItemId", item.getId());
//			res.put("product", p);
			res.put("success", true);
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}

	@DeleteMapping(path = "/user-cart/remove-item/{cartItemId}")
	public ResponseEntity<?> removeItemFromCart(@PathVariable("cartItemId") Long cartItemId) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			CartItem cartItem = cartItemService.getCartItemById(cartItemId).get();
			Product p = productService.getProduct(cartItem.getProductId()).get();
			if (cartItem.getQuantity() > 1) {
//				update quantity and amount
				cartItem.setQuantity(cartItem.getQuantity() - 1);
				cartItem.setAmount(cartItem.getQuantity() * p.getPrice());
				cartItemService.updateCartItem(cartItem);
			} else {
				cartItemService.deleteCartItem(cartItem.getId());
			}

			res.put("success", "Cart Item Removed !");
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}

	@PostMapping(path = "/user-cart/add-item-by-rfid-tag/{cartId}/{rfidtag}")
	public ResponseEntity<?> addItemByRfidTag(@PathVariable("rfidtag") String rfidtag,
			@PathVariable("cartId") Long cartId) {
		Map<String, Object> res = new HashMap<String, Object>();
		Optional<Product> p = null;
		try {

			p = productService.getProductByRfidTag(rfidtag.trim());
			if (p.isEmpty()) {
				throw new Exception("Product With " + rfidtag + " Not Found !");
			}
			CartItem item=cartService.addNewItemToUserCart(cartId, p.get());

			res.put("cartItemId", item.getId());
//			res.put("product", p);   
			res.put("success", true);
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
	
	

	@GetMapping(path = "/user-cart/get-all/{userEmail}")
	public ResponseEntity<?> getCartByUserEmail(@PathVariable("userEmail") String email) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			User user = userService.getUserFromUsername(email);
			List<com.miniproject.self_checkout_app.dto.CartItem> cartDTOList = new ArrayList<com.miniproject.self_checkout_app.dto.CartItem>();
			UserCart cart = userService.getUserCartByUser(user);
			if(cart==null) {
				throw new Exception("No cart is currently active for this user!");
			}
			for (CartItem item : cart.getItems()) {
				com.miniproject.self_checkout_app.dto.CartItem c = new com.miniproject.self_checkout_app.dto.CartItem();
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
	
	
	@PostMapping(path="/user-cart/detach/{id}")
	public ResponseEntity<?> detachStoreCartWithUser(@PathVariable("id") Long userCartId){
		Map<String, Object> res=new HashMap<String, Object>();
		try {
			UserCart cart=cartService.getUserCartById(userCartId).get();
			StoreCart storeCart= storeCartService.detachStoreCartFromAnyUser(cart.getStoreCart());
			res.put("success", true);
			res.put("storeCart", storeCart);
			return ResponseEntity.ok(res);
		}catch(Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}

}
