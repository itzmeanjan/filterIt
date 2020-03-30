package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * For concurrently applying mean filter on image pixels, we need to keep obtaining neighbourhood
 * pixel intensities for a given pixel P(x, y) & calculating mean value of them ( for all three
 * color components i.e. R, G & B ) in concurrent fashion. Which is what's done here. Multiple
 * excutions of `void run()` may run at a time on different thread, leading to faster processing of
 * whole image.
 */
class MeanFilterWorker implements Runnable {

  private int order;
  private Pixel pixel;
  private BufferedImage src, sink;

  MeanFilterWorker(BufferedImage src, BufferedImage sink, Pixel pixel, int order) {
    this.src = src;
    this.sink = sink;
    this.pixel = pixel;
    this.order = order;
  }

  /**
   * Calculates mean of a set of integers belonging to [0, 255] range, returns rounded integer
   * result
   */
  private int mean(int[][] pxlVal) {
    int sum = 0;
    for (int[] i : pxlVal) {
      for (int j : i) {
        sum += j;
      }
    }
    return Math.round((float) sum / (float) Math.pow(pxlVal.length, 2));
  }

  /**
   * Given a color component name i.e. one of {R, G, B}, it'll first obtain neighbourhood pixels
   * intensity values for that color component; and compute mean of them, which will be set as pixel
   * intensity of P(x, y) at sink image for this color component
   *
   * <p>That means, this function to be called thrice with R, G & B as color param's value.
   */
  private int getMeanForColorComponent(char color) {
    return this.mean(this.pixel.getNeighbouringPixelsFromImage(this.src, color, this.order));
  }

  /**
   * Given a pixel & order of which neighbourhood around it to be obtained, it'll compute mean pixel
   * intensity values for each of those color components ( i.e. R, G & B ), and finally update sink
   * image
   *
   * <p>Assurance given, source image not to be modified
   */
  @Override
  public void run() {
    this.sink.setRGB(
        this.pixel.posY,
        this.pixel.posX,
        new Color(
                this.getMeanForColorComponent('r'),
                this.getMeanForColorComponent('g'),
                this.getMeanForColorComponent('b'))
            .getRGB());
  }
}
