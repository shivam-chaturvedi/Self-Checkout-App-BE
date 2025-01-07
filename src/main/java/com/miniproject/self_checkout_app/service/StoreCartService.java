package com.miniproject.self_checkout_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.StoreCart;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.repository.StoreCartRepository;

@Service
public class StoreCartService {
	private final StoreCartRepository storeCartRepository;
	private final UserCartService userCartService;
	
	public StoreCartService(StoreCartRepository storeCartRepository, UserCartService userCartService) {
		this.storeCartRepository=storeCartRepository;
		this.userCartService = userCartService;
	}
	
	public StoreCart createNewStoreCart(StoreCart storeCart) {
		return storeCartRepository.save(storeCart);
	}
	
	public StoreCart updateStoreCart(StoreCart storeCart) {
		return storeCartRepository.save(storeCart);
	}
	
	public StoreCart detachStoreCartFromAnyUser(StoreCart storeCart) {
		List<UserCart> linkedUserCarts= storeCart.getUserCarts();
		for(UserCart cart:linkedUserCarts) {
			if(cart.isActive()) {
//				set current active virtual user cart to false  after detaching
				cart.setActive(false);
				userCartService.updateUserCart(cart);
			}
		}
		storeCart.setCurrentUser(null);
		return storeCartRepository.save(storeCart);
	}
	
	public List<StoreCart> getAllStoreCarts(){
		return storeCartRepository.findAll();
	}
	
	public Optional<StoreCart> getStoreCartById(Long id) {
		return storeCartRepository.findById(id);
	}
	
	public StoreCart attachStoreCartWithUser(StoreCart storeCart,User user) throws Exception {
		if(storeCart.isActive()) {
			storeCart.setCurrentUser(user);
		}
		else {
			throw new Exception("Store Cart Is Not Currently Active !");
		}
		return storeCartRepository.save(storeCart);
	}
	
	
	
}
