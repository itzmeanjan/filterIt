package in.itzmeanjan.filterit.smoothing;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.morphology.Dilation;
import in.itzmeanjan.filterit.morphology.Erosion;

import java.awt.image.BufferedImage;

/**
 * Implementation of image smoothing op - Closing
 * <p>
 * - First dilate image
 * <p>
 * - Then erode it
 * <p>
 * Order of kernels i.e. structuring elements to be used for morphological ops,
 * to be specified while invoking `smooth` method
 */
public class Closing {

    /**
     * Smooths image using ordered combination morphological op - Dilation & Erosion
     *
     * @param img           Image to be smoothed
     * @param orderDilation Order of kernel i.e. structuring element to be used for Dilation
     * @param orderErosion  Order of kernel i.e. structuring element to be used for Erosion
     * @return Smoother image with more foreground pixels
     */
    public BufferedImage smooth(BufferedImage img, int orderDilation, int orderErosion) {
        if (img == null) {
            return null;
        }
        return new Erosion().erode(new Dilation().dilate(img, orderDilation, 1), orderErosion, 1);
    }

    /**
     * Smooths image using ordered combination morphological op - Dilation & Erosion
     *
     * @param img           Image to be smoothed
     * @param orderDilation Order of kernel i.e. structuring element to be used for Dilation
     * @param orderErosion  Order of kernel i.e. structuring element to be used for Erosion
     * @return Smoother image with more foreground pixels
     */
    public BufferedImage smooth(String img, int orderDilation, int orderErosion) {
        return this.smooth(ImportExportImage.importImage(img), orderDilation, orderErosion);
    }
}
