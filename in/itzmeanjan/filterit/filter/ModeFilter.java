package in.itzmeanjan.filterit.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Pixel;

/**
 * Implementation of mode filter i.e. replaces each pixel intensity value by its
 * neighbouring pixel intensities max value
 */
public class ModeFilter implements Filter {

    /**
     * Computes max amplitude pixel intensity value from neighborhood of a certain
     * pixel ( inclusive )
     */
    private int mode(int[][] pxlVal) {
        int max = Integer.MIN_VALUE;
        for (int[] i : pxlVal)
            for (int j : i)
                if (j > max)
                    max = j;
        return max;
    }

    /**
     * Given one image & order value, we'll apply mode filter on each pixel & return
     * modified image
     */
    @Override
    public BufferedImage filter(BufferedImage img, int order) {
        if (img instanceof BufferedImage) {
            BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                    result.setRGB(j, i,
                            (new Color(this.mode(pxl.getNeighbouringPixelsFromImage(img, 'r', order)),
                                    this.mode(pxl.getNeighbouringPixelsFromImage(img, 'g', order)),
                                    this.mode(pxl.getNeighbouringPixelsFromImage(img, 'b', order))).getRGB()));
                }
            }
            return result;
        }
        return null;
    }

    @Override
    public BufferedImage filter(String src, int order) {
        return this.filter(ImportExportImage.importImage(src), order);
    }

    /**
     * Obtains name of this specific filter
     */
    @Override
    public String filterName() {
        return "Mode Filter";
    }

}