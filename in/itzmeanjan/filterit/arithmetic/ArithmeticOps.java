package in.itzmeanjan.filterit.arithmetic;

import java.awt.image.BufferedImage;

/**
 * Several arithmetic operations can be applied on two images of equal dimensions ( both width &
 * height ), which are to be implementing following interface, because they will be interacted with
 * using these API(s)
 */
interface ArithmeticOps {

  /** Checks whether two supplied images can be operated or not */
  boolean isEligible(BufferedImage operandOne, BufferedImage operandTwo);

  /** Applies binary operator on each pixel pair from two buffered images */
  BufferedImage operate(BufferedImage operandOne, BufferedImage operandTwo, boolean clip);

  /** Applies binary operator on each pixel pair from two images ( filepath given ) */
  BufferedImage operate(String operandOne, String operandTwo, boolean clip);
}
