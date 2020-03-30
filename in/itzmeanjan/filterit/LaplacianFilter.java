package in.itzmeanjan.filterit;

import in.itzmeanjan.filterit.filter.Filter;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class LaplacianFilter implements Filter {

  private int[][] getMask() {
    return new int[][] {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
  }

  private int[][] applyMask(int[][] mask, int[][] pixelV) {
    int[][] tmp = new int[pixelV.length][pixelV[0].length];
    for (int i = 0; i < pixelV.length; i++)
      for (int j = 0; j < pixelV[i].length; j++) tmp[i][j] = pixelV[i][j] * mask[i][j];
    return tmp;
  }

  private int computePartial(int[][] pixelV) {
    int partial = 0;
    for (int[] i : pixelV) for (int j : i) partial += j;
    return partial;
  }

  private int computeGradient(int gx, int gy) {
    return ((int) Math.round(Math.sqrt(Math.pow(gx, 2) + Math.pow(gy, 2))))
        % 256; // because images are 8bit RGB
    // images, so we can't let
    // intensity be > 255, which
    // is why normalization is
    // required
  }

  @Override
  public BufferedImage filter(BufferedImage img, int order) {
    if (img == null) {
      return null;
    }
    int[][] mask = this.getMask();
    BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
        result.setRGB(
            j,
            i,
            (new Color(
                    this.computeGradient(
                        this.computePartial(
                            this.applyMask(
                                mask, pxl.getNeighbouringPixelsFromImage(img, 'r', order))),
                        0),
                    this.computeGradient(
                        this.computePartial(
                            this.applyMask(
                                mask, pxl.getNeighbouringPixelsFromImage(img, 'g', order))),
                        0),
                    this.computeGradient(
                        this.computePartial(
                            this.applyMask(
                                mask, pxl.getNeighbouringPixelsFromImage(img, 'b', order))),
                        0))
                .getRGB()));
      }
    }
    return result;
  }

  @Override
  public BufferedImage filter(String src, int order) {
    return this.filter(ImportExportImage.importImage(src), order);
  }

  @Override
  public String filterName() {
    return "Laplacian Filter";
  }
}
