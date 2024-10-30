package com.miniproject.self_checkout_app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miniproject.self_checkout_app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
}