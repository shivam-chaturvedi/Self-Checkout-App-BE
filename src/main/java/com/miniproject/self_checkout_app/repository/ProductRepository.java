package com.miniproject.self_checkout_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniproject.self_checkout_app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Optional<Product> findByName(String name);
	List<Product> findByCategory(String name);
	List<Product> findByIsAvailable(boolean value);
	Optional<Product> findByRfidTag(String rfidTag);
	boolean existsById(Long id);
}
