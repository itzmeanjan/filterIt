package in.itzmeanjan.filterit.morphology;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.filter.ModeFilter;

import java.awt.image.BufferedImage;

/**
 * Given an image with white foreground objects & black background color, it'll try to
 * expand foreground area by applying a kernel on each pixel of image, which will make
 * this pixel foreground ( white ) if at least one of the pixels present in its order-1
 * neighbourhood is white colored i.e. we're extending foreground region by this morphological op.
 * <p>
 * This operation can also be helpful in detecting edges, by performing image subtraction.
 */
public class Dilation {

    /**
     * Dilation can be performed by simply applying mode filter
     * ( picks high intensity pixel from neighbourhood ) iteratively for number of specified times.
     *
     * @param img   Source image, to be dilated
     * @param order Size of structuring element
     * @param itr   Number of times dilation to be performed
     * @return Dilated image
     */
    public BufferedImage dilate(BufferedImage img, int order, int itr) {
        if (img == null || itr < 1) {
            return null;
        }
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        sink.setData(img.getData());
        ModeFilter modeFilter = new ModeFilter();
        for (int i = 0; i < itr; i++) {
            sink = modeFilter.filter(sink, order);
        }
        return sink;
    }

    /**
     * Applies dilation on image given as filepath
     *
     * @param img   Source image, to be dilated
     * @param order Size of structuring element
     * @param itr   Number of times dilation to be performed
     * @return Dilated image
     */
    public BufferedImage dilate(String img, int order, int itr) {
        return this.dilate(ImportExportImage.importImage(img), order, itr);
    }
}
