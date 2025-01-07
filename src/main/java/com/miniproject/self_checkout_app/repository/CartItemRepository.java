package com.miniproject.self_checkout_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    List<CartItem> findByProductId(Long productId);
    List<CartItem> findByUserCart(UserCart userCart);
    Optional<CartItem> findByUserCartAndProductId(UserCart userCart,Long productId);
}
