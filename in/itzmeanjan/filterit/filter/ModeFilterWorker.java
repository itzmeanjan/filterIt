package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Worker to be thrown to threads available at thread pool at a given time, for computing max pixel
 * intensity value around a specific pixel location; neighbourhood is defined by order of filtering
 */
class ModeFilterWorker implements Runnable {

  private int order;
  private Pixel pixel;
  private BufferedImage src, sink;

  ModeFilterWorker(BufferedImage src, BufferedImage sink, Pixel pixel, int order) {
    this.src = src; // image on which filter to be applied
    this.sink = sink; // filtered image, to be returned by ModeFilter.filter
    this.pixel = pixel;
    this.order = order;
  }

  /**
   * Computes max amplitude pixel intensity value from neighborhood of a certain pixel ( inclusive )
   */
  private int mode(int[][] pxlVal) {
    int max = Integer.MIN_VALUE;
    for (int[] i : pxlVal) for (int j : i) if (j > max) max = j;
    return max;
  }

  /**
   * Obtains mode value for a given color component ( R, G or B ), from its neighbourhood, specified
   * by `order`
   */
  private int getModeForColorComponent(char color) {
    return this.mode(this.pixel.getNeighbouringPixelsFromImage(this.src, color, this.order));
  }

  @Override
  public void run() {
    this.sink.setRGB(
        this.pixel.posY,
        this.pixel.posX,
        new Color(
                this.getModeForColorComponent('r'),
                this.getModeForColorComponent('g'),
                this.getModeForColorComponent('b'))
            .getRGB());
  }
}
