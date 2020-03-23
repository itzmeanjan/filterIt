package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Given an image ( either color or grayscaled ), we'll trasform each pixel
 * intensity value by using one logarithm based function & output image to be
 * buffered.
 * 
 * Transformation can be controlled by changing `base` value of logarithm,
 * mostly we'll use e or 10.
 */
public class LogTransformation {

    /**
     * Given an instance of BufferedImage class & logarithm base value i.e. 10 or e,
     * it'll compute transformed pixel intensity values for each color component (
     * R, G or B ), which is to be stored in a new BufferedImage object, which will
     * be returned
     * 
     * Now concurrent processing using thread pool has been implemented, initially
     * starting with as many number of threads ( in threadpool ) as many CPU cores
     * are made avaiable to JVM
     */
    BufferedImage transform(BufferedImage img, double base) {
        if (img == null)
            return null;
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                eService.execute(new LogTransformationWorker(i, j, base, new Color(img.getRGB(j, i)), transformed));
            }
        }
        eService.shutdown();
        return transformed;
    }

    /**
     * Given a source image file, which will be buffered and above defined method to
     * be invoked with that buffered image, returns transformed image
     */
    BufferedImage transform(String src, double base) {
        return this.transform(ImportExportImage.importImage(src), base);
    }

}