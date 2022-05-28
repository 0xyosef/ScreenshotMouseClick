package com.joo.screenshotmouseclick.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFController {

    public void createPDF(BufferedImage[] images, String path, String title) throws IOException {
        PDDocument document = new PDDocument();
        // Set the metadata
        setMetadata(title, document);

        // Create the pages, by images number
        for (BufferedImage image : images) {
            document.addPage(
                    new PDPage(
                            new PDRectangle(0, 0, image.getWidth(), image.getHeight())));
        }

        // Add the images to the pages
        addImagesToPages(images, document);
        // Save the document
        document.save(path + (path.endsWith(".pdf") ? "" : ".pdf"));
        // Close the document
        document.close();
    }

    private void addImagesToPages(BufferedImage[] images, PDDocument document) {
        for (int i = 0; i < images.length; i++) {
            try {
                // Crate the cache for the image
                File image = CacheManager.addImage(images[i]);
                // Get the image bytes from the image and add to the PDImageXObject
                PDImageXObject pdImage = PDImageXObject.createFromFile(image.getPath(), document);
                // Add the image to the page
                PDPageContentStream pdPageContentStream =
                        new PDPageContentStream(document, document.getPage(i));
                // Add the image to the page
                pdPageContentStream.drawImage(pdImage, 0, 0);
                // Close the stream
                pdPageContentStream.close();
                // Remove the image from the cache
                image.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setMetadata(String title, PDDocument document) {
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
