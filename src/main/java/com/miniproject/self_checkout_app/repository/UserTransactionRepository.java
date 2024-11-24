package com.miniproject.self_checkout_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.UserTransaction;

@Repository
public interface UserTransactionRepository  extends JpaRepository<UserTransaction, Long>{

	UserTransaction findByUser(User user);
	
}
