package in.itzmeanjan.filterit.rotation.util;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Transpose;
import in.itzmeanjan.filterit.rotation.Rotation;
import in.itzmeanjan.filterit.rotation.VerticalRotation;
import java.awt.image.BufferedImage;

/**
 * Given a buffered image, we'll rotate that image by 270° in anti-clockwise direction and obtain a
 * new image, though original image buffer to stay unmodified. We'll perform this rotation as
 * combination two basic operations
 *
 * <p>i > First vertically rotate image matrix
 *
 * <p>ii > Then transpose image matrix
 *
 * <p>Now, note that, it's really required to apply aforementioned two transformations in same order
 * as they were defined, otherwise that'll result into rotation by 90° in anti-clockwise direction
 */
public class RotateAntiClockwise270 implements Rotation {
  @Override
  public BufferedImage rotate(BufferedImage img) {
    return new Transpose().transpose(new VerticalRotation().rotate(img));
  }

  @Override
  public BufferedImage rotate(String src) {
    return this.rotate(ImportExportImage.importImage(src));
  }
}
