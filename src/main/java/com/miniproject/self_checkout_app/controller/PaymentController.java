package com.miniproject.self_checkout_app.controller;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.self_checkout_app.model.UserCart;
import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.model.UserTransaction;
import com.miniproject.self_checkout_app.service.UserCartService;
import com.miniproject.self_checkout_app.service.ProductService;
import com.miniproject.self_checkout_app.service.StoreCartService;
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
	private final ProductService productService;
	private final StoreCartService storeCartService;
	private final UserCartService cartService;

	@Value("${razorpay.key}")
	private String RAZORPAY_KEY;

	@Value("${razorpay.secret}")
	private String RAZORPAY_SECRET;

	public PaymentController(UserTransactionService userTransactionService, UserService userService,ProductService productService,UserCartService cartService, StoreCartService storeCartService) {
		this.userTransactionService = userTransactionService;
		this.userService = userService;
		this.storeCartService = storeCartService;
		this.cartService=cartService;
		this.productService=productService;
	}

	// Create an order on Razorpay
	@PostMapping("/create-order/{userEmail}")
	public ResponseEntity<?> createOrder(@PathVariable("userEmail") String userEmail) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			// Initialize Razorpay Client
			RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

			// Order details using JSONObject

			org.json.JSONObject options = new org.json.JSONObject();

			double amount=0;

			UserTransaction userTransaction=null;
//            creating transaction
			User user=userService.getUserFromUsername(userEmail);
//			this will get current cart for user
			UserCart cart=userService.getUserCartByUser(user);

			for(CartItem item:cart.getItems()){
				Product p=productService.getProduct(item.getProductId()).get();
				if(item.getQuantity()<=p.getQuantity()) {
					amount+=item.getQuantity()*p.getPrice();	
					
				}else {
					throw new Exception("Product "+p.getName()+" Quantity Not Available!");
				}
			};
			
			if (amount < 1) {
				response.put("error", "amount must be greater than 0");
				return ResponseEntity.badRequest().body(response);
			}
			
			
//			System.out.println(userTransaction);
		
			if(userTransactionService.getTransactionFromUserCart(cart).isEmpty()) {
				userTransaction= new UserTransaction();
			}
			else {	
				userTransaction= userTransactionService.getTransactionFromUserCart(cart).get();
			}
			userTransaction.setAmount(amount);
			userTransaction.setUserCart(cart);
			userTransaction.setUser(user);
			userTransaction = userTransactionService.createTransaction(userTransaction);
			
		
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
				User u=userTransaction.getUser();
//				if payment is successfull then based on user transaction cart update quantity 
//				getting current active cart 
				UserCart cart=userService.getUserCartByUser(u);
				 for(CartItem c:cart.getItems()) {
					 Product p=productService.getProduct(c.getProductId()).get();
//					 update quantity of products if transaction is successfull
					 p.setQuantity(c.getQuantity());
					 
					 productService.updateProduct(p);
				}

//				 update cart also and set is current cart to false
				 cart.setActive(false);
				 cartService.updateUserCart(cart);
			 
//				 detach cart for that user if payment is successfull
				 storeCartService.detachStoreCartFromAnyUser(cart.getStoreCart());
//				this will update the transaction status 
				userTransactionService.updateTransaction(userTransaction);
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
