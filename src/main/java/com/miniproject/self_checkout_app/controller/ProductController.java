package com.miniproject.self_checkout_app.controller;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.service.ProductService;
import com.miniproject.self_checkout_app.utils.QRCodeGenerator;

@RestController
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping(path = "/admin/add/product")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		try {
			Product p = productService.addNewProduct(product);
			resMsg.put("success", p);
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}

	@PutMapping(path = "/admin/update/product")
	public ResponseEntity<?> updateProduct(@RequestBody Product product) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		try {
			Product p = productService.updateProduct(product);
			resMsg.put("success", p);
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}

	@GetMapping(path = "/admin/product/get-all")
	public ResponseEntity<?> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping(path = "/product/get/{id}")
	public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) throws Exception {
		Map<String,Object> res=new HashMap<String, Object>();
		Optional<Product> product = productService.getProduct(productId);
		if (product.isEmpty()) {
			res.put("error", "Product with Id = " + productId + " not found !");
			return ResponseEntity.badRequest().body(res);
		} else {
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
	}	

	@DeleteMapping(path = "/admin/product/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
	}

	@GetMapping(path = "/product/qr/{id}")
	public ResponseEntity<?> getQrCode(@PathVariable("id") Long id) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			// Generate QR code for the specified product's ID, throw an exception if not
			// found
			Product p = productService.getProduct(id)
					.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

			outputStream = QRCodeGenerator.generateBarcode(p.getId().toString());

			// Set headers for file download
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=qr_code_"+p.getName()+".png");

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
