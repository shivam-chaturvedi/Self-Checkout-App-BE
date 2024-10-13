package com.miniproject.self_checkout_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Optional<Product> getProduct(Long id) {
		return productRepository.findById(id);
	}
}
