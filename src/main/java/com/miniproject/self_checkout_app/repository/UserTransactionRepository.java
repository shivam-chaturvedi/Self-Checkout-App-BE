package com.miniproject.self_checkout_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.model.UserTransaction;

@Repository
public interface UserTransactionRepository  extends JpaRepository<UserTransaction, Long>{
	List<UserTransaction> findByUser(User user);
	Optional<UserTransaction> findByUserCart(UserCart userCart);
}
 