package com.miniproject.self_checkout_app.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Optional<Product> getProduct(Long id) {
		return productRepository.findById(id);
	}
	
	public List<Product> saveAll(List<Product> products) {
		return productRepository.saveAll(products);
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	public Optional<Product> findByName(String name) {
	    return productRepository.findByName(name);
	}

	
	public static List<Product> csvToProducts(MultipartFile file) {
        List<Product> products = new ArrayList<>();
        
        try (InputStream is = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
             
            String line;
            // Skip the header if present
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header line
                }
                
                String[] fields = line.split(",");
                if (fields.length == 2) { // Ensure name and price are present
                    String name = fields[0].trim();
                    double price = Double.parseDouble(fields[1].trim());

                    Product product = new Product();
                    product.setName(name);
                    product.setPrice(price);
                    
                    products.add(product);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Log error for debugging
        }

        return products;
    }

	
	
}
