package in.itzmeanjan.filterit.rotation.util;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Transpose;
import in.itzmeanjan.filterit.rotation.HorizontalRotation;
import in.itzmeanjan.filterit.rotation.Rotation;
import java.awt.image.BufferedImage;

/**
 * Given an image, we'll rotate it by 90° in clockwise direction, using combination of two basic
 * operations
 *
 * <p>i > First transpose image matrix ( T )
 *
 * <p>ii > Then horizontally rotate image matrix ( H )
 *
 * <p>R = result image = H( T ( Image ) )
 *
 * <p>Note that : Step i & ii needs to be applied in ordered fashion, otherwise that will result
 * into rotating image by 270° in clockwise direction
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
