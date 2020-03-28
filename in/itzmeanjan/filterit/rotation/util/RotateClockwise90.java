package in.itzmeanjan.filterit.rotation.util;

import java.awt.image.BufferedImage;
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Transpose;
import in.itzmeanjan.filterit.rotation.HorizontalRotation;
import in.itzmeanjan.filterit.rotation.Rotation;

/**
 * Given an image, we'll rotate it by 90° in clockwise direction, using
 * combination of two basic operations
 *
 * i > First transpose image matrix ( T )
 *
 * ii > Then horizontally rotate image matrix ( H )
 *
 * R = result image = H( T ( Image ) )
 *
 * Note that : Step i & ii needs to be applied in ordered fashion, otherwise
 * that will result into rotating image by 270° in clockwise direction
 */
public class RotateClockwise90 implements Rotation {
  @Override
  public BufferedImage rotate(BufferedImage img) {
    return new HorizontalRotation().rotate(new Transpose().transpose(img));
  }

  @Override
  public BufferedImage rotate(String src) {
    return this.rotate(ImportExportImage.importImage(src));
  }
}
