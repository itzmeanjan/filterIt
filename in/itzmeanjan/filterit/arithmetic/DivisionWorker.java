package in.itzmeanjan.filterit.arithmetic;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Each row from two operand images get processed by this worker i.e. multiple rows can be processed
 * at a time, because they don't have any dependency on each other.
 */
class DivisionWorker implements Runnable {

  private int row;
  private Color[] colorsOne, colorsTwo;
  private BufferedImage sink;

  DivisionWorker(int row, Color[] colorsOne, Color[] colorsTwo, BufferedImage sink) {
    this.row = row;
    this.colorsOne = colorsOne;
    this.colorsTwo = colorsTwo;
    this.sink = sink;
  }

  /**
   * Performs division operation on two pixels ( from two different images ), here's a convension
   * being followed,
   *
   * <p>i \> If divisor's pixel intensity if 0, we'll put quotient 255, max pixel intensity
   *
   * <p>ii \> Otherwise floating point division will be performed, and rounded value to be stored
   */
  private Color divide(Color a, Color b) {
    return new Color(
        b.getRed() == 0 ? 255 : Math.round((((double) a.getRed()) / b.getRed())),
        b.getGreen() == 0 ? 255 : Math.round(((double) a.getGreen()) / b.getGreen()),
        b.getBlue() == 0 ? 255 : Math.round(((double) a.getBlue()) / b.getBlue()));
  }

  @Override
  public void run() {
    for (int i = 0; i < this.sink.getWidth(); i++)
      this.sink.setRGB(i, this.row, this.divide(colorsOne[i], colorsTwo[i]).getRGB());
  }
}
