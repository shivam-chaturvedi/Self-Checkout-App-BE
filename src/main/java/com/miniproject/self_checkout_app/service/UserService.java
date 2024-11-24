package com.miniproject.self_checkout_app.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


	public User signupNewUser(User user) {
		try {
			if (userRepository.existsByEmail(user.getEmail())) {
				throw new RuntimeException("Already Exists !");
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Already Exists !");
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		user.setRole(user.getRole().toUpperCase());
		user.setPassword(passwordEncoder().encode(user.getPassword()).trim());
		return userRepository.save(user);
	}
	
	/**
	 * 
	 * @param username
	 * @return true if user is admin
	 * else false
	 */
	public boolean isUserAdmin(String username) {
		try {
			User u=userRepository.findByEmail(username).get();
			if(u.getRole().equals("ADMIN")) {
				return true;
			}
			return false;
		}
		catch(Exception e) {
			return false;
		}
	}
	

	public User updateUser(User user) {
		user.setRole(user.getRole().toUpperCase());
		user.setPassword(passwordEncoder().encode(user.getPassword()).trim());
		return userRepository.save(user);
	}
	
	public void removeUser(String email) {
		try {
			Optional<User> user=userRepository.findByEmail(email);
			if(user.isEmpty()) {
				throw new Exception("User Not Found!");
			}
			userRepository.delete(user.get());
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	

	public boolean authenticateUser(User user) {
		try {
			User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(
					() -> new UsernameNotFoundException("User Not Found with username " + user.getEmail()));
			if (passwordEncoder().matches(user.getPassword().trim(), user1.getPassword())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}
	
	/**
	 * get userDeatils from username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username " + username));

		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword()).roles(user.getRole()).build();

		return userDetails;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	/**
	 * gets user by username
	 * @param token
	 * @param jwtUtil
	 * @return
	 * @throws NoSuchElementException
	 */
	public User getUserFromUsername(String username) throws NoSuchElementException {
		User user=userRepository.findByEmail(username).get();			
		return user;
	}

}
