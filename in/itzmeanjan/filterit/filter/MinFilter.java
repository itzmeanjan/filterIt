package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;

// filters only minimum intensity
// pixels from neighborhood of a pixel
// i.e. lets dark pixel grow or pass through filter
public class MinFilter implements Filter {

  // computes min intensity pixel value
  // from neighborhood of a certain pixel ( inclusive )
  private int min(int[][] pxlVal) {
    int min = Integer.MAX_VALUE;
    for (int[] i : pxlVal) for (int j : i) if (j < min) min = j;
    return min;
  }

  // applies mode filter on given image & updates pixels
  // of target image
  @Override
  public BufferedImage filter(BufferedImage img, int order) {
    if (img == null) {
      return null;
    }
    BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
        result.setRGB(
            j,
            i,
            (new Color(
                    this.min(pxl.getNeighbouringPixelsFromImage(img, 'r', order)),
                    this.min(pxl.getNeighbouringPixelsFromImage(img, 'g', order)),
                    this.min(pxl.getNeighbouringPixelsFromImage(img, 'b', order)))
                .getRGB()));
      }
    }
    return result;
  }

  @Override
  public BufferedImage filter(String src, int order) {
    return this.filter(ImportExportImage.importImage(src), order);
  }

  // obtains name of this specific filter
  @Override
  public String filterName() {
    return "Min Filter";
  }
}
