package com.miniproject.self_checkout_app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.UserTransaction;
import com.miniproject.self_checkout_app.service.UserService;
import com.miniproject.self_checkout_app.service.UserTransactionService;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentController {

	private final UserTransactionService userTransactionService;
	private final UserService userService;

	@Value("${razorpay.key}")
	private String RAZORPAY_KEY;

	@Value("${razorpay.secret}")
	private String RAZORPAY_SECRET;

	public PaymentController(UserTransactionService userTransactionService, UserService userService) {
		this.userTransactionService = userTransactionService;
		this.userService = userService;
	}

	// Create an order on Razorpay
	@PostMapping("/create-order")
	public ResponseEntity<?> createOrder(@RequestBody Map<String, String> Bill) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			// Initialize Razorpay Client
			RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

			// Order details using JSONObject
			double amount = Double.parseDouble(Bill.get("amount")); // Amount in paise (e.g., 500.00 INR)
			
			org.json.JSONObject options = new org.json.JSONObject();

//            creating transaction
			UserTransaction userTransaction = new UserTransaction();
			userTransaction.setAmount(amount);
			User user = userService.getUserFromUsername(Bill.get("username"));
			userTransaction.setUser(user);

			userTransaction = userTransactionService.createTransaction(userTransaction);
//			System.out.println(userTransaction);

			options.put("amount", userTransaction.getAmount()*100); //amount in paisa
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
	public ResponseEntity<?> verifyPayment(@RequestParam("payment_id") String paymentId,@RequestParam("transaction_id") Long transactionId) {
		try {
			// Initialize Razorpay Client
			RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

			// Fetch the payment details
			Payment payment = razorpay.Payments.fetch(paymentId);

			// Check the payment status
			if ("captured".equals(payment.get("status"))) {
				UserTransaction userTransaction=userTransactionService.getTransactionById(transactionId);
				userTransaction.setStatus("Completed");
//				this will update the transaction status 
				userTransactionService.createTransaction(userTransaction);
				return ResponseEntity.ok("Payment is successful and captured.");
			} else {
				return ResponseEntity.status(400).body("Payment verification failed.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Error while verifying payment: " + e.getMessage());
		}
	}
}
