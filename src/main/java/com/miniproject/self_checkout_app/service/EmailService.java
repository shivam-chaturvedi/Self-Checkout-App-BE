package com.miniproject.self_checkout_app.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.model.UserTransaction;
import com.miniproject.self_checkout_app.utils.BillGenerator;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final BillGenerator billGenerator;
    private final ProductService productService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender javaMailSender, BillGenerator billGenerator, ProductService productService) {
        this.mailSender = javaMailSender;
        this.billGenerator = billGenerator;
		this.productService = productService;
    }

    public void sendBillConfirmation(String to, UserTransaction userTransaction) {
        try {
            List<CartItem> items = userTransaction.getUserCart().getItems();

            StringBuilder tableRows = new StringBuilder();
            double total = 0;

            for (CartItem item : items) {
            	Product product =productService.getProduct(item.getProductId()).get(); 
            	
                String name = product.getName();
                Long quantity = item.getQuantity();
                double price = item.getAmount()/item.getQuantity();
                double amount = item.getAmount();
                total += amount;

                tableRows.append("<tr>")
                        .append("<td>").append(name).append("</td>")
                        .append("<td>").append(quantity).append("</td>")
                        .append("<td>₹").append(String.format("%.2f", price)).append("</td>")
                        .append("<td>₹").append(String.format("%.2f", amount)).append("</td>")
                        .append("</tr>");
            }

            String subject = "🧾 RetailEdge Payment Confirmation";
            String body = """
                    <p>Dear Customer,</p>
                    <p>Thank you for shopping with <strong>RetailEdge</strong>!</p>
                    <p>Your payment has been received successfully. Below are your order details:</p>
                    <table border='1' cellpadding='8' cellspacing='0'>
                      <thead>
                        <tr><th>Item</th><th>Quantity</th><th>Price</th><th>Amount</th></tr>
                      </thead>
                      <tbody>
                    """ + tableRows + """
                      </tbody>
                    </table>
                    <p><strong>Total Bill:</strong> ₹""" + String.format("%.2f", total) 
                    + 
                   "<p>Your invoice is attached in PDF format.</p><p>Regards,<br>RetailEdge Team</p>";
                

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            ByteArrayOutputStream pdfStream = this.billGenerator.getBillReport(userTransaction);
            helper.addAttachment("RetailEdge_Invoice.pdf", new ByteArrayResource(pdfStream.toByteArray()));

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
