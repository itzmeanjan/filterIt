package in.itzmeanjan.filterit.affine;

import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given a buffered image it'll compute translated buffered image by
 * translating each pixel by given amount of translation along X-axis / Y-axis or both.
 */
public class Translate {

    /**
     * Given a buffered image, we'll pick all pixel intensities
     * ( along width ) from given row index
     * @param img From where pixels to be picked up
     * @param row From this row pixels to be picked up
     * @return Selected pixel intensities as Array ( from given row of image )
     */
    private Color[] pickRow(BufferedImage img, int row) {
        Color[] colors = new Color[img.getWidth()];
        for (int i = 0; i < img.getWidth(); i++) {
            colors[i] = new Color(img.getRGB(i, row));
        }
        return colors;
    }

    /**
     * Given a buffered image, it'll concurrently translate each pixel and put them
     * into a different sink image i.e. source image doesn't get modified
     *
     * @param img Image to be translated
     * @param x Amount of translation along X-axis
     * @param y Amount of translation along Y-axis
     * @return Translated image
     */
    public BufferedImage translate(BufferedImage img, int x, int y) {
        if (img == null) {
            return null;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        sink = ImportExportImage.setCanvas(sink, new Color(0, 0, 0));
        for (int i = 0; i < img.getHeight(); i++) {
            executorService.execute(
                    new TranslateWorker(
                            sink,
                            this.pickRow(img, i),
                            i,
                            x,
                            y
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
     * Given path to image file, it'll translate that by requested amount
     * along X-axis & / or Y-axis
     *
     * @param img Path to image file to be translated
     * @param x Translation along X-axis
     * @param y Translation along Y-axis
     * @return Translated image
     */
    public BufferedImage translate(String img, int x, int y) {
        return this.translate(ImportExportImage.importImage(img), x, y);
    }
}
