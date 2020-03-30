package in.itzmeanjan.filterit.transform;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import in.itzmeanjan.filterit.ImportExportImage;

/**
 * Inverse log transformation just works opposite of Log Transformation, it
 * reduces pixel intensity values, which eventually makes image darker
 * 
 * Check examples at
 * https://github.com/itzmeanjan/filterIt/blob/master/docs/inverseLogTransformation.md
 */
public class InverseLogTransformation extends LogTransformation {

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
    @Override
    public BufferedImage transform(BufferedImage img, double base) {
        if (img == null)
            return null;
        double k = this.getK(base, 255);
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                eService.execute(
                        new InverseLogTransformationWorker(i, j, base, k, new Color(img.getRGB(j, i)), transformed));
            }
        }
        eService.shutdown();
        try {
            // waiting to complete all running workers
            eService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ie) {
            eService.shutdownNow();
            transformed = null;
        }
        return transformed;
    }

    /**
     * Given a source image file, which will be buffered and above defined method to
     * be invoked with that buffered image, returns transformed image
     */
    @Override
    public BufferedImage transform(String src, double base) {
        return this.transform(ImportExportImage.importImage(src), base);
    }
}