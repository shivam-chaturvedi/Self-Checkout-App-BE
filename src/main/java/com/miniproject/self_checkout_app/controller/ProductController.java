package com.miniproject.self_checkout_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		Product p=productService.addProduct(product);
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<>(p,headers,HttpStatus.CREATED);
	}
	
	@GetMapping(path="/get-all")
	public ResponseEntity<?> getAllProducts(){
		List<Product> products=productService.getAllProducts();
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@GetMapping(path="get/{id}")
	public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) throws Exception{
		Optional<Product> product=productService.getProduct(productId);
		if(product.isEmpty()) {
			return ResponseEntity.badRequest().body("\"Product with id="+ productId +" not found !\"");
		}
		else {
			return new ResponseEntity<>(product,HttpStatus.OK);			
		}
	}
}
