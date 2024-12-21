package com.miniproject.self_checkout_app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.User;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long>{
    List<CartItem> findByProductId(Long productId);
    List<CartItem> findByUser(User user);
}
