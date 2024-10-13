package com.miniproject.self_checkout_app.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptions {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> error(Exception e){
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/josn");
		return new ResponseEntity<>(e.getLocalizedMessage(),headers,HttpStatus.BAD_REQUEST);
	}
}
