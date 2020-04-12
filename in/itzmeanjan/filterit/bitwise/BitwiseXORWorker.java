package in.itzmeanjan.filterit.bitwise;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Actual worker class to be invoked & thrown at thread pool for concurrent computation of bitwise
 * XOR-ed pixel intensity values for a selected rows from operand images
 */
class BitwiseXORWorker implements Runnable {

  private int row;
  private Color[] colorsOne, colorsTwo;
  private BufferedImage sink;

  BitwiseXORWorker(int row, Color[] colorsOne, Color[] colorsTwo, BufferedImage sink) {
    this.row = row;
    this.colorsOne = colorsOne;
    this.colorsTwo = colorsTwo;
    this.sink = sink;
  }

  /**
   * Given two pixel intensities from two different images, it'll compute resulting pixel intensity
   * by performing bitwise AND operation on each pixel intensity values
   */
  private Color xor(Color a, Color b) {
    return new Color(
        a.getRed() ^ b.getRed(), a.getGreen() ^ b.getGreen(), a.getBlue() ^ b.getBlue());
  }

  @Override
  public void run() {
    for (int i = 0; i < this.sink.getWidth(); i++)
      this.sink.setRGB(i, this.row, this.xor(colorsOne[i], colorsTwo[i]).getRGB());
  }
}
