package in.itzmeanjan.filterit;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

/**
 * Helper class to read content of an image into BufferedImage object & write
 * BufferedImage into image file
 */
class ImportExportImage {
    /**
     * Reads content of specified image file into a BufferedImage object
     */
    public static BufferedImage importImage(String src) {
        try {
            return ImageIO.read(new File(src));
        } catch (IOException io) {
            return null;
        }
    }

    /**
     * Exports content of BufferedImage into a sink file
     */
    public static ReturnVal exportImage(BufferedImage img, String sink) {
        try {
            if (img == null)
                throw new IOException("Null image buffer");
            ImageIO.write(img, ImportExportImage.imageExtension(sink), new File(sink));
            return new ReturnVal(0, "success");
        } catch (IOException io) {
            return new ReturnVal(1, io.toString());
        }
    }

    /**
     * Given an image file name, obtains extension of that image i.e. jpg / png
     */
    public static String imageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}