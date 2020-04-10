package in.itzmeanjan.filterit.bitwise;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Bitwise OR operation being applied on each row of two different images of equal dimention, work
 * performed on different therad of worker, i.e. concurrently multiple threads can be processing
 * multiple rows at a time.
 */
class BitwiseORWorker implements Runnable {

  private int row;
  private Color[] colorsOne, colorsTwo;
  private BufferedImage sink;

  BitwiseORWorker(int row, Color[] colorsOne, Color[] colorsTwo, BufferedImage sink) {
    this.row = row;
    this.colorsOne = colorsOne;
    this.colorsTwo = colorsTwo;
    this.sink = sink;
  }

  /**
   * Given two pixel intensities from two different images, it'll compute resulting pixel intensity
   * by performing bitwise OR operation on each pixel intensity values
   */
  private Color or(Color a, Color b) {
    return new Color(
        a.getRed() | b.getRed(), a.getGreen() | b.getGreen(), a.getBlue() | b.getBlue());
  }

  @Override
  public void run() {
    for (int i = 0; i < this.sink.getWidth(); i++)
      this.sink.setRGB(i, this.row, this.or(colorsOne[i], colorsTwo[i]).getRGB());
  }
}
