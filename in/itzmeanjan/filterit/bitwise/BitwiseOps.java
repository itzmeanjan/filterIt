package in.itzmeanjan.filterit.bitwise;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Abstract class which is to be extended by all image bitwise operators ( OR, AND, XOR etc. ),
 * holds public abstract methods using which bitwise operators can be interacted with.
 */
abstract class BitwiseOps {

  /** Applies bitwise operator on each pixel pair from two buffered images */
  abstract BufferedImage operate(BufferedImage operandOne, BufferedImage operandTwo);

  /** Applies bitwise operator on each pixel pair from two images ( filepath given ) */
  abstract BufferedImage operate(String operandOne, String operandTwo);

  /**
   * Given a buffered image & row index, it'll extract out all pixel intensities present along that
   * row
   */
  protected Color[] extractRow(int row, BufferedImage img) {
    Color[] colors = new Color[img.getWidth()];
    for (int i = 0; i < img.getWidth(); colors[i] = new Color(img.getRGB(i++, row))) ;
    return colors;
  }

  /**
   * Operand images on which we'll apply any bitwise operation needs to be of same dimension, which
   * is what's ensured here
   */
  protected boolean isEligible(BufferedImage operandOne, BufferedImage operandTwo) {
    return operandOne != null
        && operandTwo != null
        && operandOne.getWidth() == operandTwo.getWidth()
        && operandOne.getHeight() == operandTwo.getHeight();
  }
}
