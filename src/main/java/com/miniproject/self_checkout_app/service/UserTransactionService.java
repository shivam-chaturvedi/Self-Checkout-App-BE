package com.miniproject.self_checkout_app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.model.UserTransaction;
import com.miniproject.self_checkout_app.repository.UserTransactionRepository;

@Service
public class UserTransactionService {
	private final UserTransactionRepository userTransactionRepository;
	
	public UserTransactionService(UserTransactionRepository userTransactionRepository) {
		this.userTransactionRepository=userTransactionRepository;
	}
	
	public UserTransaction createTransaction(UserTransaction userTransaction) {
		return userTransactionRepository.save(userTransaction);
	}
	
	public UserTransaction updateTransaction(UserTransaction userTransaction) {
		return userTransactionRepository.save(userTransaction);
	}
	
	public UserTransaction getTransactionById(Long id) {
		return userTransactionRepository.findById(id).get();
	}
	
	public Optional<UserTransaction> getTransactionFromUserCart(UserCart userCart){
		return userTransactionRepository.findByUserCart(userCart);
	}
	
	
}
