package in.itzmeanjan.filterit.edgedetection;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.arithmetic.Subtraction;
import in.itzmeanjan.filterit.morphology.Dilation;

import java.awt.image.BufferedImage;

/**
 * Implements edge detection using dilation
 * <p>
 * - First grayscale image
 * <p>
 * - Then dilate gray scaled image ( iterate for 1 time )
 * <p>
 * - And at last subtract gray scaled image from dilated one
 * <p>
 * - And you've edge highlighted image
 */
public class EdgeDetectionUsingDilation {
    /**
     * First applies dilation and then subtracts source image from dilated image,
     * resulting into edge highlighted image
     *
     * @param img   Image to be edge detected
     * @param order Size of structuring element to be used in Dilation
     * @return Edge highlighted image
     */
    public BufferedImage detect(BufferedImage img, int order) {
        if (img == null) {
            return null;
        }
        BufferedImage gray = new GrayScale().grayscale(img);
        return new Subtraction().operate(new Dilation().dilate(gray, order, 1), gray, true);
    }

    /**
     * First applies dilation and then subtracts source image from dilated image,
     * resulting into edge highlighted image
     *
     * @param img   Image to be edge detected
     * @param order Size of structuring element to be used in Dilation
     * @return Edge detected image
     */
    public BufferedImage detect(String img, int order) {
        return this.detect(ImportExportImage.importImage(img), order);
    }
}
