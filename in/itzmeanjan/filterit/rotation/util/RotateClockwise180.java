package in.itzmeanjan.filterit.rotation.util;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.rotation.HorizontalRotation;
import in.itzmeanjan.filterit.rotation.Rotation;
import in.itzmeanjan.filterit.rotation.VerticalRotation;
import java.awt.image.BufferedImage;

/**
 * Given an image, we'll rotate it by 180° in clockwise direction, using combination of two basic
 * operations
 *
 * <p>i ) First horizontally rotate image matrix ( H )
 *
 * <p>ii ) Then vertically rotate image matrix ( V )
 *
 * <p>R = result image = V( H ( Image ) ) = H( V ( Image ) )
 *
 * <p>Note that : Unordered execution of previous two steps does result into same target image i.e.
 * rotates image by 180° in clockwise direction
 */
public class RotateClockwise180 implements Rotation {

  @Override
  public BufferedImage rotate(BufferedImage img) {
    return new VerticalRotation().rotate(new HorizontalRotation().rotate(img));
  }

  @Override
  public BufferedImage rotate(String src) {
    return this.rotate(ImportExportImage.importImage(src));
  }
}
