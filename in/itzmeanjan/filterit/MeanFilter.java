package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;

class MeanFilter implements Filter {

    // calculates mean of a set of integers,
    // returns rounded value
    private int mean(int[][] pxlVal) {
        int sum = 0;
        for (int[] i : pxlVal) {
            for (int j : i) {
                sum += j;
            }
        }
        return Math.round((float) sum / (float) Math.pow(pxlVal.length, 2));
    }

    // applies mean filter on given image & updates pixels
    // of target image
    @Override
    public BufferedImage filter(BufferedImage img, int order) {
        if (img == null) {
            return null;
        }
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                result.setRGB(j, i,
                        (new Color(this.mean(pxl.getNeighbouringPixelsFromImage(img, 'r', order)),
                                this.mean(pxl.getNeighbouringPixelsFromImage(img, 'g', order)),
                                this.mean(pxl.getNeighbouringPixelsFromImage(img, 'b', order))).getRGB()));
            }
        }
        return result;
    }

    @Override
    public BufferedImage filter(String src, int order) {
        return this.filter(ImportExportImage.importImage(src), order);
    }

    // obtains name of this specific filter
    @Override
    public String filterName() {
        return "Mean Filter";
    }
}