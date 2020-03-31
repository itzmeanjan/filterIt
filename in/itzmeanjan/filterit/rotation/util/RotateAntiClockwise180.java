package in.itzmeanjan.filterit.rotation.util;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.rotation.*;
import java.awt.image.BufferedImage;

/**
 * Given a buffered image, we'll rotate that image by 180° in anti-clockwise direction and obtain a
 * new image, though original image buffer to stay unmodified. We'll perform this rotation as
 * combination two basic operations
 *
 * <p>i ) First horizontally rotate image matrix
 *
 * <p>ii ) Then vertically rotate it
 *
 * <p>or the other way :) Applying these two rotations in any of two possible orders will result
 * into same image matrix, which is rotating image by 180° in anti-clockwise / clockwise direction (
 * both are same actually )
 */
public class RotateAntiClockwise180 implements Rotation {

  @Override
  public BufferedImage rotate(BufferedImage img) {
    return new VerticalRotation().rotate(new HorizontalRotation().rotate(img));
  }

  @Override
  public BufferedImage rotate(String src) {
    return this.rotate(ImportExportImage.importImage(src));
  }
}
