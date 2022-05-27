package com.joo.screenshotmouseclick;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.joo.screenshotmouseclick.listners.MouseListener;
import com.joo.screenshotmouseclick.listners.ShortCutsListener;
import com.joo.screenshotmouseclick.pdf.PDFController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    private Robot robot;
    private ArrayList<BufferedImage> images;
    private PDFController pdfController;
    private Rectangle capture;
    private String path ="Image";

    MainController()  {
        init();
    }

    public void saveScreenshots() {
        // Test
        pdfController.createPDF(images.toArray(new BufferedImage[0]), path, "Screenshot");
        System.exit(0);
    }

    public void init(){
        try {
            robot = new Robot();
            addGlobalMouseListener();
            addGlobalKeyListener();
        } catch (AWTException | NativeHookException e) {
            e.printStackTrace();
            System.exit(1); // Exit if there is an error
        }
        capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        pdfController = new PDFController();
        images = new ArrayList<>();
    }

    private void addGlobalKeyListener() {
        GlobalScreen.addNativeKeyListener(new ShortCutsListener(this));
    }

    public void takeScreenshot(Point mousePoint){
       images.add(robot.createScreenCapture(capture));
       drawPointer(images.get(images.size()-1), mousePoint);
    }

    private void drawPointer(BufferedImage image, Point mousePoint) {
        Graphics2D g = image.createGraphics();
        g.setColor(Color.RED);
        // Draw a mouse pointer
        g.drawImage(
                new ImageIcon("resources/cursor1.png").getImage(),
                mousePoint.x,
                mousePoint.y,
                null);
        g.dispose();
    }

    public void saveScreenshotAsImage(BufferedImage image, String path) throws IOException {
        File file = new File(path);
        file.mkdir();
        int count = file.list().length;
        ImageIO.write(image, "jpg", new File(path + "/" + count + ".jpg"));
        System.out.println("Image saved");

    }
    private void addGlobalMouseListener() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeMouseListener(new MouseListener(this));
    }
}