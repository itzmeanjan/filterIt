package in.itzmeanjan.filterit.rotation;

import java.awt.image.BufferedImage;

/**
 * To be implemented by horizontally & vertically rotating classes
 * 
 * Returns a new buffered image, with out modifying on top of source image
 */
interface Rotation {
    abstract BufferedImage rotate(BufferedImage img);

    abstract BufferedImage rotate(String src);
}