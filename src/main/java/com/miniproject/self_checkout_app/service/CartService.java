package com.miniproject.self_checkout_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.repository.CartRepository;

@Service
public class CartService {
	private final CartRepository cartRepository;

	public CartService(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}

	public CartItem addItemToCart(CartItem item) {
		return cartRepository.save(item);
	}

	public CartItem getCartItemByProductId(Long productId) {
		return cartRepository.findByProductId(productId).stream().filter(CartItem::isCurrentCartItem).findFirst()
				.orElse(null);
	}

	public List<CartItem> getCartByUser(User user) {
		return cartRepository.findByUser(user).stream().filter(CartItem::isCurrentCartItem).toList();
	}
	
	public void deleteCartItem(Long id) {
		cartRepository.deleteById(id);
	}
	
	public Optional<CartItem> getCartItemById(Long id) {
		return cartRepository.findById(id);
	}
	
}
