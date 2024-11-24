package com.miniproject.self_checkout_app.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    public static ByteArrayOutputStream generateBarcode(String barcodeContent) throws WriterException, IOException {
        // Configure Barcode generation
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // Use CODE_128 format for barcodes
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

        // Create a barcode
        com.google.zxing.Writer barcodeWriter = new com.google.zxing.MultiFormatWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeContent, barcodeFormat, 600, 200, hints);

        // Convert BitMatrix to BufferedImage
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Write the image to ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();

        return baos; // Return PNG image as a byte array
    }

}
