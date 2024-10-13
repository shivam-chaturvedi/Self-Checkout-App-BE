package com.miniproject.self_checkout_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniproject.self_checkout_app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
}
