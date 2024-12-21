package com.miniproject.self_checkout_app.dto;

import com.miniproject.self_checkout_app.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	private Long cartId;

    private Long quantity=0L;
    
    private double amount=0;
    
	private Product product; 
}
