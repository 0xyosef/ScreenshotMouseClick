package com.joo.screenshotmouseclick.pdf;

import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PDFController {
    private final PDDocument document;

    public PDFController() {
        document = new PDDocument();
    }

    public void createPDF(BufferedImage[] images, String path, String title) {
        // Set the metadata
        setMetadata(title);

        // Create the pages, by images number
        for (BufferedImage image : images) {
            document.addPage(
                    new PDPage(
                            new PDRectangle(0, 0, image.getWidth(), image.getHeight())));
        }

        // Add the images to the pages
        addImagesToPages(images);

        try {
            // Save the document
            document.save(path);
            // Close the document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addImagesToPages(BufferedImage[] images) {
        for (int i = 0; i < images.length; i++) {
            try {
                // Get the image bytes from the image and add to the PDImageXObject
                PDImageXObject pdImage = PDImageXObject.
                        createFromByteArray(document, imageToBytes(images[i]), "image" + i);
                // Add the image to the page
                PDPageContentStream pdPageContentStream =
                        new PDPageContentStream(document, document.getPage(i));
                // Add the image to the page
                pdPageContentStream.drawImage(pdImage, 0, 0);
                // Close the stream
                pdPageContentStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] imageToBytes(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    private void setMetadata(String title) {
        PDDocumentInformation info = document.getDocumentInformation();
        info.setTitle(title);
        info.setAuthor("ScreenshotMouseClick by Joo and anos");
        info.setSubject("ScreenshotMouseClick");
        info.setKeywords("Screenshot, Screenshots");
        info.setCreator(System.getProperty("user.name"));
        info.setProducer("ScreenshotMouseClick");

        document.setDocumentInformation(info);
    }
}
