package com.miniproject.self_checkout_app.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
			Product p = productService.save(product);
			resMsg.put("success", p);
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}

	@PutMapping(path = "/admin/update/product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Long productId, @RequestBody Product product) {
		Map<String, Object> resMsg = new HashMap<String, Object>();
		try {
			System.out.println(product);
			Product p = productService.save(product);
			resMsg.put("success", p);
			return ResponseEntity.ok(resMsg);
		} catch (Exception e) {
			resMsg.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}
	}

	@GetMapping(path = "/product/get-all")
	public ResponseEntity<?> getAllProducts() {
		System.out.println("Jrkejqhrqejk");
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping(path = "/product/get/{id}")
	public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) throws Exception {
		Optional<Product> product = productService.getProduct(productId);
		if (product.isEmpty()) {
			return ResponseEntity.badRequest().body("\"Product with id=" + productId + " not found !\"");
		} else {
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
	}
	
	
	
	@Deprecated
	@PostMapping(path = "admin/product/upload", consumes = "multipart/form-data")
	public ResponseEntity<?> bulkUpload(@RequestParam("products") MultipartFile products) {
		Map<String, Object> resMsg = new HashMap<>();
		List<Product> newAddedProducts = new ArrayList<>(); // Properly initialize the list

		if (products.isEmpty()) {
			System.out.println("No file uploaded!");
			resMsg.put("error", "Uploaded file is empty!");
			return ResponseEntity.badRequest().body(resMsg);
		}

		try {
			// Parse CSV to List<Product>
			List<Product> parsedProducts = ProductService.csvToProducts(products);

			for (Product product : parsedProducts) {
				// Check if the product exists by name
				Optional<Product> existingProductOpt = productService.getProduct(product.getName());

				if (existingProductOpt.isPresent()) {
					// Update the existing product with new values
					Product existingProduct = existingProductOpt.get();
					existingProduct.setPrice(product.getPrice()); // Update price
					existingProduct.setCategory(product.getCategory()); // Update category
					existingProduct.setQuantity(product.getQuantity()); // Update quantity
					productService.save(existingProduct); // Save updated product
				} else {
					// Save new product
					newAddedProducts.add(product);
					productService.save(product);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			resMsg.put("error", "An error occurred while processing the file: " + e.getMessage());
			return ResponseEntity.badRequest().body(resMsg);
		}

		resMsg.put("message", "Bulk upload processed successfully!");
		resMsg.put("newAddedProducts", newAddedProducts);
		return ResponseEntity.ok(resMsg);
	}
	

	@DeleteMapping(path = "/admin/product/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
	}

	@GetMapping(path = "/admin/product/qr/{id}")
	public ResponseEntity<byte[]> getQrCode(@PathVariable("id") Long id) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			// Generate QR code for the specified product's ID, throw an exception if not
			// found
			Product p = productService.getProduct(id)
					.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

			outputStream = QRCodeGenerator.generateQRCode(p);

			// Set headers for file download
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=qr_code.png");

			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
