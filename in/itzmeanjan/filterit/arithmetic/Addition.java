package in.itzmeanjan.filterit.arithmetic;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given two images of same dimention it'll apply addition operation on each pixel pair & return
 * modified image
 */
public class Addition implements ArithmeticOps {

  /**
   * Given a buffered image & row index, it'll extract out all pixel intensities present along that
   * row
   */
  private Color[] extractRow(int row, BufferedImage img) {
    Color[] colors = new Color[img.getWidth()];
    for (int i = 0; i < img.getWidth(); colors[i] = new Color(img.getRGB(i++, row))) ;
    return colors;
  }

  /** Operand images on which we'll apply any arithmetic operation needs to be of same dimension */
  @Override
  public boolean isEligible(BufferedImage operandOne, BufferedImage operandTwo) {
    return operandOne != null
        && operandTwo != null
        && operandOne.getWidth() == operandTwo.getWidth()
        && operandOne.getHeight() == operandTwo.getHeight();
  }

  /**
   * Concurrently applies addition operator on two buffered images of same dimension, where each row
   * gets processed in a different thread, making overall execution faster, leveraging power of
   * multicore CPUs
   */
  @Override
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
          new AdditionWorker(
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

  /**
   * Invokes previous implementation, after reading two operand images into buffered image object
   */
  @Override
  public BufferedImage operate(String operandOne, String operandTwo) {
    return this.operate(
        ImportExportImage.importImage(operandOne), ImportExportImage.importImage(operandTwo));
  }
}
