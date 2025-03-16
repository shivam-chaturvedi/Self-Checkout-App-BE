package com.miniproject.self_checkout_app.service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.dto.SoldProductDto;
import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.model.StoreCart;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.repository.UserCartRepository;

import jakarta.transaction.Transactional;

@Service
public class UserCartService {

    private final UserCartRepository userCartRepository;
    private final CartItemService cartItemService;
    private final ProductService productService;

    public UserCartService(UserCartRepository userCartRepository, CartItemService cartItemService, ProductService productService) {
        this.userCartRepository = userCartRepository;
		
		this.cartItemService = cartItemService;
		this.productService = productService;
    }

    /**
     * Retrieves a UserCart by its unique ID.
     *
     * @param id the unique identifier of the UserCart
     * @return an Optional containing the UserCart if found, otherwise empty
     */
    public Optional<UserCart> getUserCartById(Long id) {
        return userCartRepository.findById(id);
    }

    /**
     * Generates a new virtual UserCart for managing user transaction history and
     * storing current cart items. 
     * 
     * - The `StoreCart` represents the physical cart in the store. 
     * - The `UserCart` is a virtual cart that maintains the user's cart history 
     *   and records items for the current session.
     * 
     * @param storeCart the StoreCart associated with the user
     * @return the newly created UserCart
     * @throws Exception if the StoreCart is not attached to any user
     */
    public UserCart getVirtualUserCart(StoreCart storeCart) throws Exception {
        if (storeCart.getCurrentUser() != null) {
        	User user=storeCart.getCurrentUser();
        	List<UserCart> activeCarts=user.getCart().stream().filter(UserCart::isActive).toList();
        	if(!activeCarts.isEmpty()) {
//        		it means there is already a active cart for this user so simply return taht cart only
        		return activeCarts.getFirst();
        	}
            UserCart userCart = new UserCart();
            userCart.setActive(true);
            userCart.setStoreCart(storeCart);
            userCart.setUser(storeCart.getCurrentUser());
            return userCartRepository.save(userCart);
        }
        throw new Exception("Store Cart is not attached to any user!");
    }
    

    /**
     * Retrieves the active UserCart for a given user.
     *
     * @param user the user whose active cart is to be retrieved
     * @return the active UserCart, or null if no active cart exists
     */
    public UserCart getActiveUserCartByUser(User user) {
        return userCartRepository.findByUser(user)
                .stream()
                .filter(UserCart::isActive)
                .findFirst()
                .orElse(null); // Use findFirst() to avoid potential exceptions
    }

    /**
     * Checks if a user already has an active cart attached.
     *
     * @param user the user to check
     * @param cart the UserCart to verify
     * @return true if the user already has an active cart, false otherwise
     */
    public boolean isCartAlreadyAttached(User user, UserCart cart) {
        return userCartRepository.findByUser(user)
                .stream()
                .filter(UserCart::isActive)
                .count() > 0;
    }
    
    public UserCart updateUserCart(UserCart userCart) {
    	return userCartRepository.save(userCart);
    }
    
    @Transactional
    public CartItem addNewItemToUserCart(Long userCartId,Product product) throws Exception {
    	return cartItemService.addItemToCart(product, userCartId);
    }
    
    public Map<String,SoldProductDto> getAllSoldProducts() {
    	try {
    		Map<String,SoldProductDto> soldProducts=new HashMap<>();
    		List<UserCart> carts=userCartRepository.findByTransactionStatus("Completed");
    		SoldProductDto soldProductDto=null;
    		for(UserCart cart:carts) {
    			if(cart.getTransaction()!=null && cart.getTransaction().getStatus().equals("Completed")) {
    				for(CartItem item:cart.getItems()) {
    					Product product=productService.getProduct(item.getProductId()).get();
//    					LocalDate date=item.getUpdatedAt().toLocalDate();
    					if(soldProducts.containsKey(product.getName())) {
    						soldProductDto= soldProducts.get(product.getName());
    					}
    					else {
        					soldProductDto=new SoldProductDto();
    					}
    					
						soldProductDto.setTotalSoldAmount(item.getAmount()+soldProductDto.getTotalSoldAmount());
						soldProductDto.setTotalQuantitySold(item.getQuantity()+soldProductDto.getTotalQuantitySold());
						
						Map<LocalDateTime,Long> soldQuantityMap=new HashMap<LocalDateTime, Long>();
						soldQuantityMap.put(item.getUpdatedAt(), item.getQuantity());
						
						soldProductDto.getTimeStampsAndQuantitySold().add(soldQuantityMap);
						
						soldProductDto.setCategory(product.getCategory());
						soldProductDto.setProductName(product.getName());
						soldProductDto.setProductPrice(product.getPrice());
						soldProductDto.setQuantityLeftInStock(product.getQuantity());
						
						soldProducts.put(product.getName(), soldProductDto);
    				}
    			}
    		}
    		return soldProducts;
    	}catch(Exception e) {
    		return null;
    	}
    }
    
  }
