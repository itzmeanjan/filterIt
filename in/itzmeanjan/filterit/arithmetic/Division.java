package in.itzmeanjan.filterit.arithmetic;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given two images of equal dimension, it'll divide pixel intensity of first image by pixel
 * intensity of second image, and store rounded value in resulting image, for each pixel, for each
 * color component. Works with both grayscaled / color image.
 */
public class Division {

  private Color[] extractRow(int row, BufferedImage img) {
    Color[] colors = new Color[img.getWidth()];
    for (int i = 0; i < img.getWidth(); colors[i] = new Color(img.getRGB(i++, row))) ;
    return colors;
  }

  /** Operand images on which we'll apply any arithmetic operation needs to be of same dimension */
  private boolean isEligible(BufferedImage operandOne, BufferedImage operandTwo) {
    return operandOne != null
        && operandTwo != null
        && operandOne.getWidth() == operandTwo.getWidth()
        && operandOne.getHeight() == operandTwo.getHeight();
  }

  /**
   * Two buffered images are supplied, we'll extract each row of pixels from two images & apply
   * division operator on each pixel, resulting image to be stored in different buffered image,
   * which can be further processed / exported into file. Each row will be processed concurrently,
   * leveraging power of modern multicore CPU.
   */
  public BufferedImage operate(BufferedImage operandOne, BufferedImage operandTwo) {
    if (!this.isEligible(operandOne, operandTwo)) {
      return null;
    }
    ExecutorService eService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    BufferedImage sink =
        new BufferedImage(operandOne.getWidth(), operandOne.getHeight(), operandOne.getType());
    for (int i = 0; i < sink.getHeight(); i++)
      eService.execute(
          new DivisionWorker(
              i, this.extractRow(i, operandOne), this.extractRow(i, operandTwo), sink));
    eService.shutdown();
    try {
      // waiting for all of those workers to complete their tasks
      eService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException ie) {
      eService.shutdownNow();
      sink = null;
    }
    return sink;
  }

  public BufferedImage operate(String operandOne, String operandTwo) {
    return this.operate(
        ImportExportImage.importImage(operandOne), ImportExportImage.importImage(operandTwo));
  }
}
