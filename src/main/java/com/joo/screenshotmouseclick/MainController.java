package com.joo.screenshotmouseclick;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainController {
    private Robot robot;
    private BufferedImage bufferedImage;
    private Dimension screenSize;
    private Rectangle capture;
    private String path ="Image";

    MainController()  {
        init();
//        try {
//            saveImage(path);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

    }

    public void saveScreenshot() {
        try {
            saveImage(path);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void init(){
        try {
            robot = new Robot();
            addGlobalMouseListener();
        } catch (AWTException | NativeHookException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenshot(){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        capture = new Rectangle(screenSize);
        bufferedImage = robot.createScreenCapture(capture);
    }

    public void saveImage(String path) throws IOException {
        File file = new File(path);
        file.mkdir();
        int count = file.list().length;
        ImageIO.write(bufferedImage, "jpg", new File(path + "/" + count + ".jpg"));
        System.out.println("Image saved");

    }
    private void addGlobalMouseListener() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeMouseListener(new MouseListener());
        GlobalScreen.addNativeMouseMotionListener(new MouseListener());
    }
}