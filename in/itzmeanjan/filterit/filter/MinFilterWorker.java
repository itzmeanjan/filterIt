package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.Pixel;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Given a specific pixel in an image, computes minimum pixel intensity in its neighbourhood; and
 * updated P(x, y) location in sink image with min intensity value.
 */
class MinFilterWorker implements Runnable {

  private int order;
  private Pixel pixel;
  private BufferedImage src, sink;

  MinFilterWorker(BufferedImage src, BufferedImage sink, Pixel pixel, int order) {
    this.src = src;
    this.sink = sink;
    this.pixel = pixel;
    this.order = order;
  }

  /* Computes min pixel intensity value from neighborhood of a certain pixel ( inclusive ) */
  private int min(int[][] pxlVal) {
    int min = Integer.MAX_VALUE;
    for (int[] i : pxlVal) for (int j : i) if (j < min) min = j;
    return min;
  }

  /**
   * Given a color component name i.e. one of {R, G, B}, it'll first obtain neighbourhood pixels
   * intensity values for that color component; and compute minimum of them, which will be set as
   * pixel intensity of P(x, y) at sink image for this color component
   *
   * <p>That means, this function to be called thrice, each time with R / G / B, as color arg value.
   */
  private int getMinForColorComponent(char color) {
    return this.min(this.pixel.getNeighbouringPixelsFromImage(this.src, color, this.order));
  }

  @Override
  public void run() {
    this.sink.setRGB(
        this.pixel.posY,
        this.pixel.posX,
        new Color(
                this.getMinForColorComponent('r'),
                this.getMinForColorComponent('g'),
                this.getMinForColorComponent('b'))
            .getRGB());
  }
}
