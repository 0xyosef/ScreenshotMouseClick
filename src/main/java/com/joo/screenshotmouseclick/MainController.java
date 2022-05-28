package com.joo.screenshotmouseclick;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.joo.screenshotmouseclick.debugg.Debugger;
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
import java.util.Objects;

public class MainController {
    private Robot robot;
    private ArrayList<BufferedImage> images;
    private PDFController pdfController;
    private Rectangle capture;
    private volatile boolean running;

    public MainController()  {
        init();
    }

    public void saveScreenshots(File target, SAVE_MODE mode) throws IOException {
        switch (mode) {
            case PDF -> pdfController.createPDF(images.toArray(new BufferedImage[0]),
                            target.getAbsolutePath(), target.getName());
            case PNG -> saveScreenshotsAsImages(target.getAbsolutePath());
        }
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
        running = false;
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

                new ImageIcon("src/main/resources/cursor1.png").getImage(),
                mousePoint.x,
                mousePoint.y,
                32,
                32,
                null);
        g.dispose();
    }

    public void saveScreenshotsAsImages(String path) throws IOException {
        File file = new File(path);
        int count = 0;
        if (file.list() != null) {
            count = Objects.requireNonNull(file.list()).length;
        }
        for (BufferedImage bufferedImage : images) {
            ImageIO.write(bufferedImage, "png",
                    new File(path + File.separator + ++count + ".png"));
            Debugger.println("Saved image " + count);
        }

    }
    private void addGlobalMouseListener() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeMouseListener(new MouseListener(this));
    }

    public boolean isRunning() {
        return running;
    }

    public void startRec() {
        running = true;
    }

    public void stopRec() {
        running = false;
    }
}