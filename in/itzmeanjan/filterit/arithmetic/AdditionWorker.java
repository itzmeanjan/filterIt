package in.itzmeanjan.filterit.arithmetic;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Applies addition operation on two row of pixels, each from two different image of same dimension;
 * modified pixel pixel intensities are scaled into proper range & stored into sink buffered image
 */
class AdditionWorker implements Runnable {

  private int row;
  private Color[] colorsOne, colorsTwo;
  private BufferedImage sink;
  private boolean clip;

  AdditionWorker(int row, Color[] colorsOne, Color[] colorsTwo, BufferedImage sink, boolean clip) {
    this.row = row;
    this.colorsOne = colorsOne;
    this.colorsTwo = colorsTwo;
    this.sink = sink;
    this.clip = clip;
  }

  /**
   * Scaling pixel intensity down to range 0-255 ( working with 24-bit three component RGB images ),
   * by applying modulas operator on pixel intensity
   *
   * <p>I(x, y) = I(x, y) % 256
   */
  private int scaleIntensity(int intensity) {
    return Math.abs(intensity) % 256;
  }

  /**
   * Clipping values < 0 to 0 & > 255 to 255, and not touching anyother value already in range [0,
   * 255], allows us to keep pixel intensities in range
   */
  private int clipIntensity(int intensity) {
    return intensity < 0 ? 0 : (intensity > 255 ? 255 : intensity);
  }

  /**
   * Given two pixel intensities from two different images, it'll compute resulting pixel intensity
   * by adding & scaling them; also clips pixel values or scales them by using modulas operator,
   * depending upon user supplied argument
   */
  private Color add(Color a, Color b) {
    return this.clip
        ? new Color(
            this.clipIntensity(a.getRed() + b.getRed()),
            this.clipIntensity(a.getGreen() + b.getGreen()),
            this.clipIntensity(a.getBlue() + b.getBlue()))
        : new Color(
            this.scaleIntensity(a.getRed() + b.getRed()),
            this.scaleIntensity(a.getGreen() + b.getGreen()),
            this.scaleIntensity(a.getBlue() + b.getBlue()));
  }

  @Override
  public void run() {
    for (int i = 0; i < this.sink.getWidth(); i++)
      this.sink.setRGB(i, this.row, this.add(colorsOne[i], colorsTwo[i]).getRGB());
  }
}
