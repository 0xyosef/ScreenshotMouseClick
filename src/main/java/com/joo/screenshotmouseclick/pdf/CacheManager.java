package com.joo.screenshotmouseclick.pdf;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CacheManager {
    private static String path;

    static {
        path = ".cache";
    }
    public static File addImage(BufferedImage image) throws IOException {
        createCacheFolder();
        File imageFile = new File(path + File.separator + image.getData().toString() + ".png");
        ImageIO.write(image, "png", imageFile);
        return imageFile;
    }

    private static void createCacheFolder() {
        File cacheFolder = new File(path);
        if (!cacheFolder.exists()) {
            cacheFolder.mkdir();
        }
    }
}
