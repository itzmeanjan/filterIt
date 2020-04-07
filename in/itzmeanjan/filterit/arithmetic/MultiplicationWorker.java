package in.itzmeanjan.filterit.arithmetic;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Each row of two seperate but equal dimension images gets processed concurrently by this worker
 * class implementation. Each pixel intensity value ( for each of those color components ) get
 * computed by multiplying pixel intensities from two operand images, and finally scaling / clipping
 * pixel intensity values, so that values get into range.
 */
class MultiplicationWorker implements Runnable {

  private int row;
  private Color[] colorsOne, colorsTwo;
  private BufferedImage sink;
  private boolean clip;

  MultiplicationWorker(
      int row, Color[] colorsOne, Color[] colorsTwo, BufferedImage sink, boolean clip) {
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
   * by multiplying them; clips pixel values or scales them by using modulas operator, depending
   * upon user supplied argument
   */
  private Color multiply(Color a, Color b) {
    return this.clip
        ? new Color(
            this.clipIntensity(a.getRed() * b.getRed()),
            this.clipIntensity(a.getGreen() * b.getGreen()),
            this.clipIntensity(a.getBlue() * b.getBlue()))
        : new Color(
            this.scaleIntensity(a.getRed() * b.getRed()),
            this.scaleIntensity(a.getGreen() * b.getGreen()),
            this.scaleIntensity(a.getBlue() * b.getBlue()));
  }

  @Override
  public void run() {
    for (int i = 0; i < this.sink.getWidth(); i++)
      this.sink.setRGB(i, this.row, this.multiply(colorsOne[i], colorsTwo[i]).getRGB());
  }
}
