package com.miniproject.self_checkout_app.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeDecoder {

//	based on esp32 camera capture  
	private static int width = 160;
	private static int height = 120;
 
	// Function to convert RGB565 data to RGB888 (BufferedImage)
	private static BufferedImage rgb565ToRgb888(ByteBuffer byteBuffer) throws IOException {
		byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);

		// Create a BufferedImage to hold the final image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Convert RGB565 to RGB888 and set pixels in the BufferedImage
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Read one 16-bit pixel (RGB565 format)
				short pixel = byteBuffer.getShort();

				// Extract RGB565 components (for big-endian format)
				int r = (pixel >> 11) & 0x1F; // 5 bits for red
				int g = (pixel >> 5) & 0x3F; // 6 bits for green
				int b = pixel & 0x1F; // 5 bits for blue

				// Scale RGB565 to RGB888 (8 bits for each channel)
				r = (r << 3); // Scale red to 8 bits
				g = (g << 2); // Scale green to 8 bits
				b = (b << 3); // Scale blue to 8 bits

				// Set the pixel in the BufferedImage (RGB format)
				int rgb = new Color(r, g, b).getRGB();
				image.setRGB(x, y, rgb);
			}
		}

		return image;
	}

	/**
	 * For better decoding
	 * @param rgb565Data
	 * @return
	 */
	public static String decodeWithZbarImg(ByteBuffer rgb565Data) {
		File outputFile = null;
		try {
			// Convert RGB565 to RGB888
			BufferedImage image = rgb565ToRgb888(rgb565Data);

			// Save the image as a BMP file
			outputFile = File.createTempFile("temp", ".bmp"); // Use a temp file for better cleanup
			ImageIO.write(image, "BMP", outputFile);

			// Use zbarimg to decode the barcode from the saved BMP file
			ProcessBuilder pb = new ProcessBuilder("zbarimg", outputFile.getAbsolutePath(),"-q");
			Process process = pb.start();

			// Capture the output of zbarimg (Standard Output)
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line = reader.readLine();

				// Wait for the process to complete
				int exitCode = process.waitFor();

				if (exitCode != 0) {
					throw new IOException("zbarimg process failed with exit code: " + exitCode);
				}

				if (line == null || !line.contains(":")) {
					throw new IOException("Invalid output from zbarimg: " + line);
				}

				// Extract and return the decoded data
				return line.split(":")[1].trim();
			}
		} catch (IOException e) {
			System.err.println("Error processing image or executing zbarimg: " + e.getMessage());
			return null;
		} catch (InterruptedException e) {
			System.err.println("Process was interrupted: " + e.getMessage());
			Thread.currentThread().interrupt(); // Preserve the interrupt status
			return null;
		} finally {
			// Ensure the temporary BMP file is deleted
			if (outputFile != null && outputFile.exists()) {
				if (!outputFile.delete()) {
					System.err.println("Failed to delete temporary file: " + outputFile.getAbsolutePath());
				}
			}
		}
	}
	
	/**
	 * For Faster But Less Accurate Decoding
	 * @param rgb565Data
	 * @return
	 */
	public static String decodeBarcodeWithZxing(ByteBuffer rgb565Data) {
		try {

			// Convert RGB565 to RGB888
			BufferedImage image = rgb565ToRgb888(rgb565Data);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			// Setup decoding hints
			Map<DecodeHintType, Object> hints = new HashMap<>();
			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE); 
			hints.put(DecodeHintType.ALLOWED_EAN_EXTENSIONS, BarcodeFormat.CODE_128);
			MultiFormatReader reader = new MultiFormatReader();

			// Attempt to decode
			Result result = reader.decode(bitmap, hints);
			return result.getText();
		} catch (NotFoundException | IOException e) {
			System.out.println("Barcode not found.");
			return null;
		}

	}

}
