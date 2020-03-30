package in.itzmeanjan.filterit.transform;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Worker class, which applies inverse transformation function on given pixel & finally updates that
 * cell in final buffered image
 */
class InverseImageTransformationWorker implements Runnable {

  private int idxI, idxJ;
  private Color color;
  private BufferedImage sink;

  /**
   * As we're planning to distribute pixel transformation task into multiple working threads, we'll
   * require to pass target buffered image object, where modifications to be made, along with pixel
   * position & corresponding intensity values ( current values ).
   */
  InverseImageTransformationWorker(int i, int j, Color color, BufferedImage sink) {
    this.idxI = i;
    this.idxJ = j;
    this.color = color;
    this.sink = sink;
  }

  /**
   * Each pixel needs to be transformed i.e. pixel intensity value of each pixel needs to be mapped
   * to different value using some function
   *
   * <p>Here that transformation function is : I(x, y) = (L - 1) - I(x, y), where L-1 = 255 for
   * 8-bit gray scaled image i.e. highest value that pixel intensity can possibly be.
   */
  private int transformPixel(int intensity, int maxIntensity) {
    return maxIntensity - intensity;
  }

  /**
   * Changes color intensity using transformation function & modifies final image buffer, which is
   * being shared among multiple workers ( but the good thing is that a single pixel position to be
   * modified by one & only one worker, so that'll never result into any kind of data inconsistency
   * )
   */
  @Override
  public void run() {
    this.sink.setRGB(
        this.idxJ,
        this.idxI,
        (new Color(
                this.transformPixel(color.getRed(), 255),
                this.transformPixel(color.getGreen(), 255),
                this.transformPixel(color.getBlue(), 255)))
            .getRGB());
  }
}
