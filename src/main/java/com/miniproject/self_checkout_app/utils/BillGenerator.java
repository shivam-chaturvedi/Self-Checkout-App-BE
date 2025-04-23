package com.miniproject.self_checkout_app.utils;

import org.springframework.stereotype.Component;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.miniproject.self_checkout_app.model.CartItem;
import com.miniproject.self_checkout_app.model.Product;
import com.miniproject.self_checkout_app.model.UserTransaction;
import com.miniproject.self_checkout_app.service.ProductService;
import com.miniproject.self_checkout_app.service.UserTransactionService;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BillGenerator {

	private final ProductService productService;

	public BillGenerator(ProductService productService) {
		this.productService = productService;
	}

	public ByteArrayOutputStream getBillReport(UserTransaction userTransaction) throws Exception {

		if (userTransaction == null) {
			return null;
		}

		if (!userTransaction.getStatus().equals("Completed")) {
			throw new Exception("Transaction is Pending (Bill Can't Be Generated) !");
		}

		Map<String, Object> data = new HashMap<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hh:mm:ss a");

		data.put("TransactionId", userTransaction.getId());
		data.put("TransactionDate", userTransaction.getUpdatedAt().format(formatter));
		data.put("Email", userTransaction.getUser().getEmail());
		data.put("Receipt", userTransaction.getReceipt());
		data.put("TransactionStatus", userTransaction.getStatus());

		List<CartItem> items = userTransaction.getUserCart().getItems();

		data.put("Items", items);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document();

		PdfWriter.getInstance(document, baos);
		document.open();
		
		try {
		InputStream is = (InputStream) getClass().getClassLoader().getResourceAsStream("logo.png");
			Image image = Image.getInstance(is.readAllBytes());
	
			image.setAbsolutePosition(20f, 780f);
			image.scaleToFit(30f, 30f);
			document.add(image);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		Font font = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, BaseColor.RED);
		String text = "Retail Edge";
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(Element.ALIGN_CENTER);

		Chunk underlineChunk = new Chunk(text, font);
		underlineChunk.setUnderline(0.6f, -2f);
		paragraph = new Paragraph(underlineChunk);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		Font customerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.NORMAL, BaseColor.GRAY);
		Paragraph customerInfo = new Paragraph("\nTransaction ID: " + data.get("TransactionId") + "\nTransaction Date: "
				+ data.get("TransactionDate") + "\nReceipt: " + data.get("Receipt") + "\nEmail: " + data.get("Email")
				+ "\nTransaction Status: " + data.get("TransactionStatus"), customerFont);
		customerInfo.setSpacingAfter(20f);
		document.add(customerInfo);

		PdfPTable table = new PdfPTable(6);
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLACK);
		BaseColor headerColor = new BaseColor(9, 230, 180);
		PdfPCell cell;

		cell = new PdfPCell(new Phrase("Item Added At\n", headerFont));
		cell.setBackgroundColor(headerColor);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Product\n", headerFont));
		cell.setBackgroundColor(headerColor);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Price\n", headerFont));
		cell.setBackgroundColor(headerColor);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Category\n", headerFont));
		cell.setBackgroundColor(headerColor);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Quantity\n", headerFont));
		cell.setBackgroundColor(headerColor);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Amount\n", headerFont));
		cell.setBackgroundColor(headerColor);
		table.addCell(cell);

		double totalCost = 0D;
		for (CartItem item : items) {
			String formattedDate = item.getUpdatedAt().format(formatter);
			table.addCell(formattedDate + "\n\n");
			Product p = productService.getProduct(item.getProductId()).get();
			table.addCell(p.getName() + "\n");
			table.addCell(p.getPrice() + "\n");
			table.addCell(p.getCategory() + "\n");
			table.addCell(item.getQuantity() + "\n");
			table.addCell(item.getAmount() + "\n");
			totalCost += item.getAmount();
		}

		table.setWidthPercentage(100);
		table.setSpacingBefore(20f);
		document.add(table);

		Font totalFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
		Paragraph totalCostParagraph = new Paragraph("Total Amount(INR): " + totalCost, totalFont);
		totalCostParagraph.setAlignment(Element.ALIGN_RIGHT);
		totalCostParagraph.setSpacingBefore(10f);
		document.add(totalCostParagraph);

		document.close();

		return baos;

	}
}
