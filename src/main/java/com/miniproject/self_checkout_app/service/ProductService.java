package com.miniproject.self_checkout_app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.repository.ProductRepository;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CartItemService cartItemService;

    public ProductService(ProductRepository productRepository, CartItemService cartItemService) {
        this.productRepository = productRepository;
        this.cartItemService = cartItemService;
    }

    /**
     * Adds a new product to the repository.
     *
     * @param product the product to add
     * @return the saved product
     */
    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Updates an existing product in the repository. If the product's price has changed,
     * it updates the associated cart items with the new price.
     *
     * @param product the product with updated details
     * @return the updated product
     */
    public Product updateProduct(Product product) {
        Product old = productRepository.findById(product.getId()).get();
        
        // Check if the product price has been updated
        if (old.getPrice() != product.getPrice()) {
            // Retrieve all cart items associated with the product
            List<CartItem> items = cartItemService.getCartItemsByProductId(product.getId());
            if (!items.isEmpty()) {
                for (CartItem item : items) {
                    // Update cart items only if the cart is active
                    if (item.getUserCart().isActive()) {
                        // Recalculate the cart item amount based on the updated product price
                        item.setAmount(item.getQuantity() * product.getPrice());
                        cartItemService.updateCartItem(item);
                    }
                }
            }
        }
        return productRepository.save(product);
    }

    /**
     * Retrieves all products from the repository.
     *
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the product ID
     * @return an optional containing the product if found, otherwise empty
     */
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Adds a list of products to the repository in bulk.
     *
     * @param products the list of products to add
     * @return a list of saved products
     */
    public List<Product> bulkAddProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the product ID
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Retrieves a product by its name.
     *
     * @param name the name of the product
     * @return an optional containing the product if found, otherwise empty
     */
    public Optional<Product> getProduct(String name) {
        return productRepository.findByName(name);
    }

    /**
     * Retrieves a product by its RFID tag.
     *
     * @param rfidTag the RFID tag of the product
     * @return an optional containing the product if found, otherwise empty
     */
    public Optional<Product> getProductByRfidTag(String rfidTag) {
        return productRepository.findByRfidTag(rfidTag);
    }

    /**
     * Retrieves all products in a specific category.
     *
     * @param category the category of the products
     * @return a list of products in the given category
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    /**
     * Retrieves all products based on their availability status.
     *
     * @param value the availability status (true for available, false for unavailable)
     * @return a list of products matching the availability status
     */
    public List<Product> getAvailableProducts(boolean value) {
        return productRepository.findByIsAvailable(value);
    }
}
