package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Given a buffered image we'll transpose it, as we do in case of matrix,
 * because 2D image is nothing but a matrix
 * 
 * Assume we've this matrix 2x2 matrix
 * 
 * 2 | 3
 * 
 * 6 | 7
 * 
 * Transpose of it,
 * 
 * 
 * 2 | 6
 * 
 * 3 | 7
 * 
 * i.e. rows get into position of columns / columns get into position of rows
 */
public class Transpose {

    /**
     * Performs transpose of image, returns transposed buffered image
     */
    public BufferedImage transpose(BufferedImage img) {
        if (img == null) {
            return null;
        }
        BufferedImage sink = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                sink.setRGB(i, j, color.getRGB());
            }
        }
        return sink;
    }

    /**
     * Given path to a image file, transposed buffered image to be returned
     */
    public BufferedImage transpose(String src) {
        return this.transpose(ImportExportImage.importImage(src));
    }
}