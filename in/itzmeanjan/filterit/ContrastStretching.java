package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Contrast Stretching is pretty similar to Histogram Equalization, but rather
 * that computing pixel intensity probability & corresponding CDF values, we'll
 * go for stretching pixel intensity range, which may be intially `a` & `b`,
 * where a > 0 && or || b < 255, but we'll make it 0 to 255. Finally dark image
 * will get brighter.
 */
public class ContrastStretching {

    /**
     * Given a grayscale image, which is already read into BufferedImage, we'll find
     * minimum pixel intensity ( >= 0)
     */
    private int minIntensity(BufferedImage img) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int tmpI = new Color(img.getRGB(j, i)).getRed();
                if (min > tmpI)
                    min = tmpI;
            }
        }
        return min;
    }

    /**
     * Same as previous one, but we'll rather find maximum intensity value, which
     * must be <=255
     */
    private int maxIntensity(BufferedImage img) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int tmpI = new Color(img.getRGB(j, i)).getRed();
                if (max < tmpI)
                    max = tmpI;
            }
        }
        return max;
    }

    /**
     * For each pixel intensity value of a grayscale image, we'll apply afore
     * defined transformation function & put transformed pixel intensity value in
     * each cell, which will be returned ( that can be either exported or processed
     * further )
     * 
     * Concurrency incorporated using java Thread pool.
     */
    BufferedImage transform(BufferedImage img) {
        if (img == null) {
            return null;
        }
        img = (new GrayScale()).grayscale(img);
        int minIntensity = this.minIntensity(img);
        int maxIntensity = this.maxIntensity(img);
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                eService.execute(new ContrastStretchingWorker(i, j, minIntensity, maxIntensity,
                        new Color(img.getRGB(j, i)), transformed));
            }
        }
        eService.shutdown();
        return transformed;
    }

    /**
     * Just another way to invoke previous transformation function, instead of
     * passing BufferedImage, image file name to be passed, which will be read &
     * modified image buffer to be returned ( using previous function )
     */
    BufferedImage transform(String src) {
        return this.transform(ImportExportImage.importImage(src));
    }
}