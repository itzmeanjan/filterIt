package in.itzmeanjan.filterit.affine;

import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Performs affine transformation - ROTATION on given image,
 * keeps source image unmodified
 */
public class Rotate {

    /**
     * Picks all pixel intensities across width of image, from
     * selected row of image matrix
     * <p>
     * Each row to be processed concurrently, which is why we need to'
     * pick rows up and submit processing in thread pool
     *
     * @param row Picks pixel intensities from this row of image matrix
     * @param img Image matrix from which pixels to be picked up
     * @return Array of pixels from buffered image
     */
    private Color[] pickRow(int row, BufferedImage img) {
        Color[] colors = new Color[img.getWidth()];
        for (int i = 0; i < img.getWidth(); i++) {
            colors[i] = new Color(img.getRGB(i, row));
        }
        return colors;
    }

    /**
     * Rotates image by given angle ( in degrees ), performs
     * concurrent processing of each row of image matrix
     *
     * @param img   Image to be rotated
     * @param theta Angle of rotation in degree
     * @return Rotated buffered image, which is other than source image
     */
    public BufferedImage rotate(BufferedImage img, double theta) {
        if (img == null) {
            return null;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            executorService.execute(
                    new RotateWorker(sink, i, theta, this.pickRow(i, img))
            );
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            sink = null;
        }
        return sink;
    }

    /**
     * Given an image by its file path, reads it,
     * and rotates, rotated image is different than
     * original i.e. in place modification not done
     *
     * @param img   Path to image file to be rotated
     * @param theta Angle of rotation in degrees
     * @return Rotated buffered image
     */
    public BufferedImage rotate(String img, double theta) {
        return this.rotate(ImportExportImage.importImage(img), theta);
    }
}
