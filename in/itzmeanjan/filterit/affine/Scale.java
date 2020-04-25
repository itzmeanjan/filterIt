package in.itzmeanjan.filterit.affine;

import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given an image, it'll scale that image as per requested x & y param values
 * in respective directions & returns transformed image ( generated
 * using affine transformation rules )
 */
public class Scale {

    /**
     * Picks up pixel intensities from given row of image
     * along width i.e. pixel count to be equal to width of image
     * <p>
     * These pixels are to be processed by different thread of workers
     *
     * @param img Buffered image from which pixels to be plucked
     * @param row Row index from which pixels to be plucked along width
     * @return Plucked pixel intensities of given row from image
     */
    private Color[] pickRow(BufferedImage img, int row) {
        Color[] colors = new Color[img.getWidth()];
        for (int i = 0; i < img.getWidth(); i++) {
            colors[i] = new Color(img.getRGB(i, row));
        }
        return colors;
    }

    /**
     * Scales pixels of given buffered image by using following formula
     * <p>
     * P[x, y] = round( P[x * sX , y * sY] )
     * <p>
     * where sX, sY are scale factors along X & Y axis respectively, they are floating point values
     *
     * @param img Buffered image to be scaled
     * @param x   Scale factor along X
     * @param y   Scale factor along X
     * @return Scaled buffered image, which is other than original image
     */
    public BufferedImage scale(BufferedImage img, double x, double y) {
        if (img == null) {
            return null;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        sink = ImportExportImage.setCanvas(sink, new Color(0, 0, 0));
        for (int i = 0; i < img.getHeight(); i++) {
            executorService.execute(
                    new ScaleWorker(
                            sink, x, y, i, this.pickRow(img, i)
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
     * Scales given image ( by file path ) by calling above
     * implemented method
     *
     * @param img Buffered image to be scaled
     * @param x   Scale factor along X
     * @param y   Scale factor along Y
     * @return Scaled buffered image
     */
    public BufferedImage scale(String img, double x, double y) {
        return this.scale(ImportExportImage.importImage(img), x, y);
    }

    /**
     * Scales buffered image along X by given scale factor
     *
     * @param img Buffered image to be scaled
     * @param x   Scale factor along X
     * @return Scaled buffered image
     */
    public BufferedImage scaleX(BufferedImage img, double x) {
        return this.scale(img, x, 1.0);
    }

    /**
     * Scales buffered image along X by given scale factor
     *
     * @param img Image to be scaled
     * @param x   Scale factor along X
     * @return Scaled image
     */
    public BufferedImage scaleX(String img, double x) {
        return this.scaleX(ImportExportImage.importImage(img), x);
    }

    /**
     * Scales buffered image along Y by given scale factor
     *
     * @param img Image to be scaled
     * @param y   Scale factor along Y
     * @return Scaled image
     */
    public BufferedImage scaleY(BufferedImage img, double y) {
        return this.scale(img, 1.0, y);
    }

    /**
     * Scales buffered image along Y by given scale factor
     *
     * @param img Image to be scaled
     * @param y   Scale factor along Y
     * @return Scaled image
     */
    public BufferedImage scaleY(String img, double y) {
        return this.scaleY(ImportExportImage.importImage(img), y);
    }
}
