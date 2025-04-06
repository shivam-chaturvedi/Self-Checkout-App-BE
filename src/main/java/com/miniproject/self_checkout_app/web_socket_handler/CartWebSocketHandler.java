package com.miniproject.self_checkout_app.web_socket_handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.model.StoreCart;
import com.miniproject.self_checkout_app.model.User;
import com.miniproject.self_checkout_app.service.ProductService;
import com.miniproject.self_checkout_app.service.StoreCartService;
import com.miniproject.self_checkout_app.service.UserCartService;
import com.miniproject.self_checkout_app.utils.QRCodeDecoder;

//ADDING T TO START OF THE MESSAGE WILL DISPLAY MESSAGE IN GREEN IN ESP32 AND ADDING F WILL DISPLAY IN RED AS A ERROR
// SENDING SHOW_PAYMENT_QR WILL DISPLAY PAYMENT QR CODE IN TFT DISPLAY 
@Component
public class CartWebSocketHandler extends AbstractWebSocketHandler {
	private final StoreCartService storeCartService;
	private final UserCartService userCartService;
	private final ProductService productService;
	private Long storeCartId = null;
	private boolean isPaymentQrCodeShowing = false;
	private final Map<Long, List<WebSocketSession>> CART_SESSIONS = new HashMap<Long, List<WebSocketSession>>();

	public CartWebSocketHandler(StoreCartService storeCartService, UserCartService userCartService,
			ProductService productService) {
		this.storeCartService = storeCartService;
		this.userCartService = userCartService;
		this.productService = productService;
	}

	public void sendTextMessageToAllSessions(String message) {
		for (WebSocketSession session : CART_SESSIONS.getOrDefault(storeCartId, new ArrayList<WebSocketSession>())) {
			if (session.isOpen()) {
				try {
					session.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws IOException {
		String query = session.getUri().getQuery();
		if (query != null && query.startsWith("cartId=")) {
			try {
				if (query.contains("&")) {
//					for non main nodemcu
					this.storeCartId = Long.parseLong(query.split("&")[0].split("=")[1]);
				} else {
					this.storeCartId = Long.parseLong(query.split("=")[1]);
				}
				Optional<StoreCart> storeCart = storeCartService.getStoreCartById(storeCartId);
				if (storeCart.isEmpty()) {
					session.sendMessage(new TextMessage("FInvalid cart ID: " + storeCartId));
					session.close();
				}
				User attachedUser = storeCart.get().getCurrentUser();
				if (attachedUser != null) {
					session.sendMessage(new TextMessage("TConnected:- " + attachedUser.getEmail()));
					CART_SESSIONS.putIfAbsent(storeCartId, new ArrayList<WebSocketSession>());
					CART_SESSIONS.get(storeCartId).add(session);

				}
			} catch (NumberFormatException e) {
				session.sendMessage(new TextMessage("FInvalid cart ID format."));
				session.close();
			}
		} else {
			session.sendMessage(new TextMessage("FMissing or invalid cart ID."));
			session.close();
		}
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
		try {
			String decodedData = QRCodeDecoder.decodeWithZbarImg(message.getPayload());
			if (decodedData != null) {
				processDecodedData(session, decodedData);
			} else {
				if (!isPaymentQrCodeShowing) {
					session.sendMessage(new TextMessage("TScan To Add Product!"));
				}
			}
		} catch (Exception e) {
			try {
				if (!isPaymentQrCodeShowing) {
					session.sendMessage(new TextMessage("FError: " + e.getMessage()));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	} 

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		try {

			String payload = message.getPayload();

			if (payload.startsWith("SHOW_PAYMENT_QR")) {
//				this will show payment qr in tft
				if (!isPaymentQrCodeShowing) {
					sendTextMessageToAllSessions(payload);
					isPaymentQrCodeShowing = true;
				}
			}

			else {
				isPaymentQrCodeShowing = false;

				Optional<StoreCart> storeCart = storeCartService.getStoreCartById(storeCartId);
				if (storeCart.isPresent()) {
					Long userCartId = storeCartService.getActiveUserCartId(storeCart.get());

					if (userCartId == null) {
						sendTextMessageToAllSessions("FNo active user cart found. Please attach a cart.");
						return;
					}
					Optional<Product> product = productService.getProductByRfidTag(payload.trim());
					if (product.isPresent()) {
						CartItem cartItem = userCartService.addNewItemToUserCart(userCartId, product.get());
						sendTextMessageToAllSessions(
								"TAdded: " + product.get().getName() + " x " + cartItem.getQuantity());
					} else {
						sendTextMessageToAllSessions("FProduct not found for scanned code.");
					}
				} else {
					session.sendMessage(new TextMessage("FInvalid store cart."));
				}

			}
		} catch (Exception e) {
			sendTextMessageToAllSessions("FError: " + e.getMessage());
		}
	}

	private void processDecodedData(WebSocketSession session, String decodedData) throws Exception {
		Optional<StoreCart> storeCart = storeCartService.getStoreCartById(storeCartId);
		if (storeCart.isPresent()) {
			Long userCartId = storeCartService.getActiveUserCartId(storeCart.get());

			if (userCartId == null) {
				sendTextMessageToAllSessions("FNo active user cart found. Please attach a cart.");
				return;
			}
			Optional<Product> product = productService.getProduct(Long.parseLong(decodedData));
			if (product.isPresent()) {
				CartItem cartItem = userCartService.addNewItemToUserCart(userCartId, product.get());
				sendTextMessageToAllSessions("TAdded: " + product.get().getName() + " x " + cartItem.getQuantity());
			} else {
				sendTextMessageToAllSessions("FProduct not found for scanned code.");
			}
		} else {
			session.sendMessage(new TextMessage("FInvalid store cart."));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		System.out.println("Connection closed: " + session.getId());
		CART_SESSIONS.getOrDefault(storeCartId, new ArrayList<WebSocketSession>()).remove(session);
	}
}
