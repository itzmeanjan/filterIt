package in.itzmeanjan.filterit.arithmetic;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Several arithmetic operations can be applied on two images of equal dimensions ( both width &
 * height ), and following abstract class defines how to talk to those operators. Each operator will
 * extend this class, while ensuring they reuse some common code from parent & also provide some
 * common mean to talk to image operators.
 */
abstract class ArithmeticOps {

  /** Applies binary operator on each pixel pair from two buffered images */
  abstract BufferedImage operate(BufferedImage operandOne, BufferedImage operandTwo, boolean clip);

  /** Applies binary operator on each pixel pair from two images ( filepath given ) */
  abstract BufferedImage operate(String operandOne, String operandTwo, boolean clip);

  /**
   * Given a buffered image & row index, it'll extract out all pixel intensities present along that
   * row
   */
  protected Color[] extractRow(int row, BufferedImage img) {
    Color[] colors = new Color[img.getWidth()];
    for (int i = 0; i < img.getWidth(); colors[i] = new Color(img.getRGB(i++, row))) ;
    return colors;
  }

  /** Operand images on which we'll apply any arithmetic operation needs to be of same dimension */
  protected boolean isEligible(BufferedImage operandOne, BufferedImage operandTwo) {
    return operandOne != null
        && operandTwo != null
        && operandOne.getWidth() == operandTwo.getWidth()
        && operandOne.getHeight() == operandTwo.getHeight();
  }
}
