package com.miniproject.self_checkout_app.web_socket_handler;

import java.io.IOException;
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

@Component
public class CartWebSocketHandler extends AbstractWebSocketHandler {
    private final StoreCartService storeCartService;
    private final UserCartService userCartService;
    private final ProductService productService;
    private Long storeCartId = null;

    public CartWebSocketHandler(StoreCartService storeCartService, UserCartService userCartService,
                                 ProductService productService) {
        this.storeCartService = storeCartService;
        this.userCartService = userCartService;
        this.productService = productService;
    }

    @Override 
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("cartId=")) {
            try {
                this.storeCartId = Long.parseLong(query.split("=")[1]);
                Optional<StoreCart> storeCart = storeCartService.getStoreCartById(storeCartId);
                if (storeCart.isEmpty()) {
                    session.sendMessage(new TextMessage("FInvalid cart ID: " + storeCartId));
                    session.close();
                }
                User attachedUser= storeCart.get().getCurrentUser();
                if(attachedUser!=null) {
                	session.sendMessage(new TextMessage("TConnected:-"+attachedUser.getEmail()));
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
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        try {
        	System.out.println(message);
            String decodedData = QRCodeDecoder.decodeWithZbarImg(message.getPayload());
            if (decodedData != null) {
                processDecodedData(session, decodedData);
            } else {
                session.sendMessage(new TextMessage("TScan To Add Product!"));
            }
        } catch (Exception e) {
            session.sendMessage(new TextMessage("FError: " + e.getMessage()));
        }
    }
   

    
    private void processDecodedData(WebSocketSession session, String decodedData) throws Exception {
        Optional<StoreCart> storeCart = storeCartService.getStoreCartById(storeCartId);
        if (storeCart.isPresent()) {
            Long  userCartId = storeCartService.getActiveUserCartId(storeCart.get());
            
            if (userCartId == null) {
                session.sendMessage(new TextMessage("FNo active user cart found. Please attach a cart."));
                return;
            }
            Optional<Product> product = productService.getProduct(Long.parseLong(decodedData));
            if (product.isPresent()) {
                CartItem cartItem = userCartService.addNewItemToUserCart(userCartId, product.get());
                session.sendMessage(new TextMessage("TAdded: " + product.get().getName() + " x " + cartItem.getQuantity()));
            } else {
                session.sendMessage(new TextMessage("FProduct not found for scanned code."));
            }
        } else {
            session.sendMessage(new TextMessage("FInvalid store cart."));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Connection closed: " + session.getId());
    }
}
