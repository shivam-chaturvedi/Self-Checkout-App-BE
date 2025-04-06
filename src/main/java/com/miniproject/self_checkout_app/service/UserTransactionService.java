package com.miniproject.self_checkout_app.service;

import java.util.ArrayList;
import java.util.List;
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
	
	public String InitRefund(Long id) {
		try {
			UserTransaction userTransaction= userTransactionRepository.findById(id).get();	
			if(userTransaction.getStatus().equals("Completed")) {
				userTransaction.setRefundStatus("Refund Initiated!");
				updateTransaction(userTransaction);
				return userTransaction.getRefundStatus();
			}
			else{
				return "Transaction is Pending ! A refund cannot be initiated!";
			}
		}
		catch(Exception e) {
			return e.getMessage();
		}
		
	}
	
	public List<UserTransaction> getInitiatedRefunds(){
		List<UserTransaction> res=new ArrayList<UserTransaction>();
		for(UserTransaction u:userTransactionRepository.findAll()) {
			if(u.getRefundStatus()!=null && u.getRefundStatus().equals("Refund Initiated!")) {
				res.add(u);
			}
		}
		return res;
	}
	
	
	
}
