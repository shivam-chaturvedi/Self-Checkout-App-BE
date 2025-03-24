package com.miniproject.self_checkout_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.SecurityImages;
import com.miniproject.self_checkout_app.repository.SecurityImagesRepository;

@Service
public class SecurityImagesService {
	private final SecurityImagesRepository securityImagesRepository;
	
	public SecurityImagesService(SecurityImagesRepository securityImagesRepository) {
		this.securityImagesRepository = securityImagesRepository;	
	}
	
	public SecurityImages saveThreatImage(SecurityImages securityImages) {
		return securityImagesRepository.save(securityImages);
	}
	
	public List<SecurityImages> getAllThreatImages() {
		return securityImagesRepository.findAll();
	}
	
}
