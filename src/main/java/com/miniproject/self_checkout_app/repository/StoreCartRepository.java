package com.miniproject.self_checkout_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject.self_checkout_app.model.StoreCart;
import com.miniproject.self_checkout_app.model.User;

@Repository
public interface StoreCartRepository extends JpaRepository<StoreCart,Long>{
	Optional<StoreCart> findByCurrentUser(User user);
}
