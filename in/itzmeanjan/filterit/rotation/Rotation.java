package in.itzmeanjan.filterit.rotation;

import java.awt.image.BufferedImage;

/**
 * To be implemented by horizontally & vertically rotating classes or any other
 * utility classes which'll try to rotate image, following two abstract methods
 * will be only way using which user gets to talk to rotation mechanism
 */
public interface Rotation {
    // pass a buffered image; return rotated image, without any how modifying
    // original image
    abstract BufferedImage rotate(BufferedImage img);

    // pass path to image, we'll first read image & then invoke previous one to get
    // desired job done
    abstract BufferedImage rotate(String src);
}