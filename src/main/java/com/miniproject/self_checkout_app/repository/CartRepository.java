package com.miniproject.self_checkout_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniproject.self_checkout_app.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long>{

}
