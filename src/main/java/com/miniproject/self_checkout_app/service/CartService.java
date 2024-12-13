package com.miniproject.self_checkout_app.service;

import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.repository.CartRepository;

@Service
public class CartService {
	private final CartRepository cartRepository;
	
	public CartService(CartRepository cartRepository) {
		this.cartRepository=cartRepository;
	}
	
	public CartItem addItemToCart(CartItem cart) {
		return cartRepository.save(cart);
	}
	
	
}
