package in.itzmeanjan.filterit.edgedetection;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.arithmetic.Subtraction;
import in.itzmeanjan.filterit.morphology.Erosion;

import java.awt.image.BufferedImage;

/**
 * Detects edges of image by applying combination of erosion & subtraction.
 * Only grayscale images supported, so implicit gray scaling to be performed,
 * inside this implementation.
 * - Applies gray scaling on given image
 * <p>
 * - Then erodes that image for 1 time
 * <p>
 * - Finally subtracts eroded image from gray scaled one
 * <p>
 * - And we've our resulting edge detected image
 */
public class EdgeDetectionUsingErosion {

    /**
     * Detects edges of image
     *
     * @param img   Image on which edges to be detected
     * @param order Size of structuring element to be used for Erosion
     * @return Edge detected image
     */
    public BufferedImage detect(BufferedImage img, int order) {
        if (img == null) {
            return null;
        }
        BufferedImage gray = new GrayScale().grayscale(img);
        return new Subtraction().operate(gray, new Erosion().erode(gray, order, 1), true);
    }

    /**
     * Detects edges of image
     *
     * @param img   Image on which edges to be detected
     * @param order Size of structuring element to be used for Erosion
     * @return Edge detected image
     */
    public BufferedImage detect(String img, int order) {
        return this.detect(ImportExportImage.importImage(img), order);
    }
}
