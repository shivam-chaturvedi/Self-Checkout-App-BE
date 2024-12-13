package com.miniproject.self_checkout_app.controller;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.self_checkout_app.dto.CreateOrderRequest;
import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.UserTransaction;
import com.miniproject.self_checkout_app.service.ProductService;
import com.miniproject.self_checkout_app.service.UserService;
import com.miniproject.self_checkout_app.service.UserTransactionService;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class PaymentController {

	private final UserTransactionService userTransactionService;
	private final UserService userService;
	private final ProductService productService;
	

	@Value("${razorpay.key}")
	private String RAZORPAY_KEY;

	@Value("${razorpay.secret}")
	private String RAZORPAY_SECRET;

	public PaymentController(UserTransactionService userTransactionService, UserService userService,ProductService productService) {
		this.userTransactionService = userTransactionService;
		this.userService = userService;
		this.productService=productService;
	}

	// Create an order on Razorpay
	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			// Initialize Razorpay Client
			RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

			// Order details using JSONObject

			org.json.JSONObject options = new org.json.JSONObject();

//            creating transaction
			double amount=0;
			UserTransaction userTransaction = new UserTransaction();
			List<CartItem> cart=new ArrayList<CartItem>();
			for(CartItem item:request.getCart()) {
				Product p=productService.getProduct(item.getId()).get();
				if(p.getQuantity()<item.getQuantity() ) {
					response.put("error", "Cart Item "+p.getName()+" Quantity not available");
					return ResponseEntity.badRequest().body(response);
				}
				amount+=p.getPrice()*item.getQuantity();
//				update quantity only in user transaction object cart  
//				p.setQuantity(p.getQuantity()-item.getQuantity());
				CartItem c=new CartItem();
				c.setProductId(p.getId());
				c.setQuantity(p.getQuantity()-item.getQuantity());
				c.setAmount(amount);
				c.setUserTransaction(userTransaction);
				cart.add(c);
				
			}
			if (amount < 1) {
				response.put("error", "amount must be greater than 0");
				return ResponseEntity.badRequest().body(response);
			}
			userTransaction.setAmount(amount);
			userTransaction.setCart(cart);
			User user = userService.getUserFromUsername(request.getUsername());
			userTransaction.setUser(user);

			userTransaction = userTransactionService.createTransaction(userTransaction);
//			System.out.println(userTransaction);

			options.put("amount", userTransaction.getAmount() * 100); // amount in paisa
			options.put("currency", userTransaction.getCurrency());
			options.put("receipt", userTransaction.getReceipt());
			options.put("payment_capture", Boolean.TRUE); // Use Boolean.TRUE instead of primitive boolean

			// Create Order
			Order order = razorpay.Orders.create(options);

			// Create a response object

			response.put("id", order.get("id")); // Razorpay Order ID
			response.put("amount", order.get("amount"));
			response.put("currency", order.get("currency"));
			response.put("transaction_id", userTransaction.getId());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
//			e.printStackTrace();
			response.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	// Verify the payment by payment ID
	@GetMapping("/verify-payment")
	public ResponseEntity<?> verifyPayment(@RequestParam("payment_id") String paymentId,
			@RequestParam("transaction_id") Long transactionId) {
		Map<String , Object> res=new HashMap<String, Object>();
		try {
			// Initialize Razorpay Client
			RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

			// Fetch the payment details
			Payment payment = razorpay.Payments.fetch(paymentId);

			// Check the payment status
			if ("captured".equals(payment.get("status"))) {
				UserTransaction userTransaction = userTransactionService.getTransactionById(transactionId);
				userTransaction.setStatus("Completed");
//				if payment is successfull then based on user transaction cart update quantity 
				 for(CartItem c:userTransaction.getCart()) {
					 Product p=productService.getProduct(c.getProductId()).get();
					 p.setQuantity(c.getQuantity());
					 productService.save(p);
				 }

//				this will update the transaction status 
				userTransactionService.createTransaction(userTransaction);
				res.put("success", "Payment is successful and captured.");
				return ResponseEntity.ok(res);
			} else {
				res.put("error", "Payment verification failed.");
				return ResponseEntity.status(400).body(res);
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(res);
		}
	}
}
