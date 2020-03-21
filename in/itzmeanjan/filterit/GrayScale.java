package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;

class GrayScale {

    /**
     * Given one already imported image i.e. contructed BufferedImage object, we'll
     * compute grayscaled image in another BufferedImage instance, which is to be
     * returned
     */
    BufferedImage grayscale(BufferedImage img) {
        if (img == null)
            return null;
        BufferedImage grayscaled = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < grayscaled.getHeight(); i++) {
            for (int j = 0; j < grayscaled.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                grayscaled.setRGB(j, i, (new Color(avg, avg, avg)).getRGB());
            }
        }
        return grayscaled;
    }

    /**
     * Reads content of source image, grayscales image and writes output into a
     * BufferedImage object, which is to be returned, can be used for further
     * processing
     */
    BufferedImage grayscale(String src) {
        return this.grayscale(ImportExportImage.importImage(src));
    }
}