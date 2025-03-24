package com.miniproject.self_checkout_app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.self_checkout_app.model.SecurityImages;
import com.miniproject.self_checkout_app.service.SecurityImagesService;


@RestController
public class SurveillanceSystem {
	private final SecurityImagesService securityImagesService;
	
	public SurveillanceSystem(SecurityImagesService securityImagesService) {
		this.securityImagesService = securityImagesService;
		
	}
	
	@PostMapping(path="/notify-threat")
	public ResponseEntity<?> notifyThreat(@RequestParam("file") MultipartFile image,@RequestParam("description") String description){
		Map<String,Object> res=new HashMap<String, Object>();
		try {
			  byte[] imageBytes = image.getBytes();
			  SecurityImages securityImages=new SecurityImages();
			  securityImages.setDescription(description);
			  securityImages.setImageData(imageBytes);
	
			  securityImages=securityImagesService.saveThreatImage(securityImages);
			  
			  res.put("success", securityImages.getId());
			  return ResponseEntity.ok(res);

		}catch(Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
	
	@GetMapping(path="/get-surveillance-images")
	public ResponseEntity<?> getSurveillanceImages(){
		Map<String,Object> res=new HashMap<String, Object>();
		List<SecurityImages> securityImages=new ArrayList<SecurityImages>();
		try {
			securityImages=securityImagesService.getAllThreatImages();
			  res.put("success", securityImages);
			  return ResponseEntity.ok(res);

		}catch(Exception e) {
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
	
	
}
