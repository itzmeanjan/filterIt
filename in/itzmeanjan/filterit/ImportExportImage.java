package in.itzmeanjan.filterit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Helper class to read content of an image into BufferedImage object & write BufferedImage into
 * image file
 */
public class ImportExportImage {
  /** Reads content of specified image file into a BufferedImage object */
  public static BufferedImage importImage(String src) {
    try {
      return ImageIO.read(new File(src));
    } catch (IOException io) {
      return null;
    }
  }

  /** Exports content of BufferedImage into a sink file */
  public static ReturnVal exportImage(BufferedImage img, String sink) {
    try {
      if (img == null) throw new IOException("Null image buffer");
      ImageIO.write(img, ImportExportImage.imageExtension(sink), new File(sink));
      return new ReturnVal(0, "success");
    } catch (IOException io) {
      return new ReturnVal(1, io.toString());
    }
  }

  /** Given an image file name, obtains extension of that image i.e. jpg / png */
  public static String imageExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  /**
   * Given a buffered image, we'll modify it by setting each pixel with
   * provided color, could be helpful in setting background of image & then draw on it.
   *
   * @param img Image to be modified
   * @param color Color value to be set at each pixel location across image I
   * @return Returns modified image buffer, in place editing performed
   */
  public static BufferedImage setCanvas(BufferedImage img, Color color){
    for(int i = 0; i< img.getHeight(); i++){
      for(int j = 0; j< img.getWidth(); j++){
        img.setRGB(j, i, color.getRGB());
      }
    }
    return img;
  }
}
