package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given a RGB image, it'll grayscale that image by taking
 * mean of intensities ( intensity of each of three color components )
 * at each pixel location of image, while not modifying original image
 * <p>
 * If you pass an grayscale image, it'll not do any harm, but that's
 * nothing but waste of computation
 */
public class GrayScale {

    /**
     * Picks a set of pixels along width of image, for a specific row
     *
     * @param img Image from which pixels to be picked
     * @param row Row index of image
     * @return Set of pixels along width of image, for a specific row index
     */
    private Color[] pickRow(BufferedImage img, int row) {
        Color[] colors = new Color[img.getWidth()];
        for (int i = 0; i < img.getWidth(); i++) {
            colors[i] = new Color(img.getRGB(i, row));
        }
        return colors;
    }

    /**
     * Given a buffered image it'll concurrently compute grayscale of that image
     */
    public BufferedImage grayscale(BufferedImage img) {
        if (img == null) {
            return null;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < sink.getHeight(); i++) {
            executorService.execute(
                    new GrayScaleWorker(
                            i,
                            this.pickRow(img, i),
                            sink
                    )
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
     * Computes grayscale of given image ( as filepath ), using above implementation
     */
    public BufferedImage grayscale(String src) {
        return this.grayscale(ImportExportImage.importImage(src));
    }
}
