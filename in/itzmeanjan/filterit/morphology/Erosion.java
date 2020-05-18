package in.itzmeanjan.filterit.morphology;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.filter.MinFilter;

import java.awt.image.BufferedImage;

/**
 * Given an image it'll try to shrink foreground region of image, where
 * foreground denoted by white color ( higher pixel intensity )
 * <p>
 * It's nothing but application of min filter on black-white image.
 */
public class Erosion {


    /**
     * Erodes given image
     *
     * @param img   Image to be eroded
     * @param order Size of square shaped structuring element
     * @param itr   Times erosion to be performed iteratively
     * @return Eroded image
     */
    public BufferedImage erode(BufferedImage img, int order, int itr) {
        if (img == null || itr < 1) {
            return null;
        }
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        sink.setData(img.getData());
        MinFilter minFilter = new MinFilter();
        for (int i = 0; i < itr; i++) {
            sink = minFilter.filter(sink, order);
        }
        return sink;
    }

    /**
     * Erodes given image
     *
     * @param img   Image to be eroded
     * @param order Size of square shaped structuring element
     * @param itr   Times erosion to be performed iteratively
     * @return Eroded image
     */
    public BufferedImage erode(String img, int order, int itr) {
        return this.erode(ImportExportImage.importImage(img), order, itr);
    }
}
