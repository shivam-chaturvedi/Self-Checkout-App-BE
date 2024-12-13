package com.miniproject.self_checkout_app.dto;

import java.util.List;
import com.miniproject.self_checkout_app.model.CartItem;

public class CreateOrderRequest {
    private String username;
    private List<CartItem> cart;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }
}



