package com.miniproject.self_checkout_app.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.service.ProductService;
import com.miniproject.self_checkout_app.utils.QRCodeGenerator;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	private ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@PostMapping(path="/add")
	public String addProduct(@ModelAttribute Product product) {
		Product p=productService.addProduct(product);
		System.out.println(p);
		return "redirect:/admin";
	}
	
	@GetMapping(path="/get-all")
	@ResponseBody
	public ResponseEntity<?> getAllProducts(){
		List<Product> products=productService.getAllProducts();
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@GetMapping(path="get/{id}")
	@ResponseBody
	public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) throws Exception{
		Optional<Product> product=productService.getProduct(productId);
		if(product.isEmpty()) {
			return ResponseEntity.badRequest().body("\"Product with id="+ productId +" not found !\"");
		}
		else {
			return new ResponseEntity<>(product,HttpStatus.OK);			
		}
	}
	
	@PostMapping(path = "/upload", consumes = "multipart/form-data")
	public String bulkUpload(@RequestParam("products") MultipartFile products) {
		List<Product> _products=new ArrayList<Product>();
	    if (products.isEmpty()) {
	        System.out.println("No file uploaded!");
	        return "redirect:/admin?error=NoFileUploaded";
	    }
	    
	    try {
	    _products=productService.csvToProducts(products);
	    productService.saveAll(_products);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/admin?error=FileUploadFailed";
	    }

	    return "redirect:/admin?success=FileUploaded";
	}
	

    @GetMapping(path = "/get-qr")
    @ResponseBody
    public ResponseEntity<byte[]> getQrCodePdf() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products available".getBytes());
        }

        try {
            // Generate QR code for the first product's ID
            String qrText = String.valueOf(products.get(1).getId());
            outputStream = QRCodeGenerator.generateQRCode(qrText);

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
