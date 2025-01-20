package com.miniproject.self_checkout_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.repository.CartItemRepository;
import jakarta.transaction.Transactional;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
        
    }

    /**
     * Adds a product to the user's cart. If the product already exists in the cart,
     * its quantity is incremented, and the total amount is updated. Otherwise, a new
     * cart item is created and added to the cart.
     *
     * @param productId the ID of the product to add
     * @param userCart  the active cart to which the product is to be added
     * @return the updated or newly created cart item
     * @throws Exception if the cart is not active
     */
    @Transactional
    public  CartItem addItemToCart(Product product, UserCart userCart) throws Exception {
        if (userCart.isActive()) {
            // Check if the product already exists in the cart
            CartItem existingItem = null;
            for (CartItem item : userCart.getItems()) {
                if (item.getProductId() == product.getId()) {
                    existingItem = item; // Found the existing item in the cart
                    break;
                }
            }

            if (existingItem != null) {
                // Increment the quantity and update the total amount
                existingItem.setQuantity(existingItem.getQuantity() + 1);
                existingItem.setAmount(existingItem.getQuantity() * product.getPrice());
                if (existingItem.getQuantity() > product.getQuantity()) {
                    throw new Exception("Product " + product.getName() + " Quantity Not Available!");
                }

                // Save and return the updated CartItem
                return cartItemRepository.save(existingItem);
            } else {
                // If no cart item exists with the given product ID, create a new one
                CartItem newItem = new CartItem();
                newItem.setProductId(product.getId());
                newItem.setQuantity(1L);
                newItem.setAmount(newItem.getQuantity() * product.getPrice());
                newItem.setUserCart(userCart);

                if (newItem.getQuantity() > product.getQuantity()) {
                    throw new Exception("Product " + product.getName() + " Quantity Not Available!");
                }

                // Save and return the newly created CartItem
                return cartItemRepository.save(newItem);
            }
        }
        throw new Exception("Cart Is Not Active!");
    }

    
    
    
    /**
     * Deletes a cart item by its ID.
     *
     * @param id the ID of the cart item to delete
     */
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    /**
     * Updates an existing cart item in the repository.
     *
     * @param cartItem the cart item with updated details
     * @return the updated cart item
     */
    public CartItem updateCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    /**
     * Retrieves a cart item by its ID.
     *
     * @param id the ID of the cart item
     * @return an optional containing the cart item if found, otherwise empty
     */
    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    /**
     * Retrieves all cart items associated with a specific product ID.
     *
     * @param productId the ID of the product
     * @return a list of cart items containing the specified product
     */
    public List<CartItem> getCartItemsByProductId(Long productId) {
        return cartItemRepository.findByProductId(productId);
    }
}
