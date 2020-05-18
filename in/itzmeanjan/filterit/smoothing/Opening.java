package in.itzmeanjan.filterit.smoothing;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.morphology.Dilation;
import in.itzmeanjan.filterit.morphology.Erosion;

import java.awt.image.BufferedImage;

/**
 * It's one of smoothing operations based on image morphological op.
 * <p>
 * - First erode image
 * <p>
 * - Then dilate it
 * <p>
 * Size of structuring element being used for each of above
 * ops, affects result of smoothing
 */
public class Opening {
    /**
     * Smooths image using ordered combination of morphological
     * ops i.e. Erosion & Dilation ( order is very important here )
     *
     * @param img           Image to be smoothed
     * @param orderErosion  Size of structuring element used for Erosion
     * @param orderDilation Size of structuring element used for Dilation
     * @return Smoothed image
     */
    public BufferedImage smooth(BufferedImage img, int orderErosion, int orderDilation) {
        if (img == null) {
            return null;
        }
        return new Dilation().dilate(new Erosion().erode(img, orderErosion, 1), orderDilation, 1);
    }

    /**
     * Smooths image using ordered combination of morphological
     * ops i.e. Erosion & Dilation ( order is very important here )
     *
     * @param img           Image to be smoothed
     * @param orderErosion  Size of structuring element used for Erosion
     * @param orderDilation Size of structuring element used for Dilation
     * @return Smoothed image
     */
    public BufferedImage smooth(String img, int orderErosion, int orderDilation) {
        return this.smooth(ImportExportImage.importImage(img), orderErosion, orderDilation);
    }
}
