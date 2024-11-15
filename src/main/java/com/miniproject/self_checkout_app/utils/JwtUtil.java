package com.miniproject.self_checkout_app.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.miniproject.self_checkout_app.service.UserService;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

	private final UserService userService;

	public JwtUtil(UserService userService) {
		this.userService = userService;
	}

	// Hardcoded Base64-encoded secret key (this should be stored securely)
	private final String SECRET_KEY_STRING = "yourhSJKSHDJKSHDKJSAHDKJSAHJKASHJKjcnckadkadjkadsjdlka";

	// JWT expiration time (default is 1 hour)
	private long expirationTime = 1000 * 60 * 60;

	// Decode the Base64 string and store it as a Key object
	private Key getSecretKey() {
		byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY_STRING);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
	}

	/**
	 * Sets the expiration time for the JWT tokens in milliseconds.
	 * 
	 * This method allows the user to configure a custom expiration time for JWT
	 * tokens, which will be used for all newly generated tokens. The expiration
	 * time is specified in seconds and will be automatically converted to
	 * milliseconds to align with the Java Date API, which requires time in
	 * milliseconds.
	 * 
	 * @param expirationTime The expiration time for JWT tokens, specified in
	 *                       seconds. It will be converted to milliseconds by
	 *                       multiplying with 1000.
	 */
	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime * 1000;
	}

	// Generate JWT token
	public String generateToken(String username) {
		JwtBuilder builder = Jwts.builder().setSubject(username) // Set the username as the subject
				.setIssuedAt(new Date()) // Set the issued date
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Set expiration time
				.signWith(getSecretKey()); // Sign with the hardcoded secret key
		return builder.compact(); // Generate and return the token
	}

	// Extract username from the token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject); // Extract the subject (username) from the claims
	}

	// Extract expiration date from the token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration); // Extract expiration from the claims
	}

	// Extract a claim from the token (e.g., username or expiration)
	private <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token); // Extract all claims from the token
		return claimsResolver.apply(claims); // Apply the claim resolver (e.g., get the subject)
	}

	// Extract all claims from the token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSecretKey()) // Use the hardcoded secret key to verify the
																	// signature
				.build().parseClaimsJws(token) // Parse the JWT and get all claims
				.getBody();
	}

	// Check if the token is expired
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date()); // Check if expiration date is before the current date
	}

	// Validate the token by checking if the username matches and the token is not
	// expired
	public boolean validateToken(String token) {
		try {
			String username = extractUsername(token);
			// if user is loaded without any exception so username is valid
			userService.loadUserByUsername(username);
			if (!isTokenExpired(token)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
