package in.itzmeanjan.filterit.arithmetic;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given two images of equal dimension, it'll multiply pixel intensity values of each image ( pixel
 * by pixel fashion ), and resulting image to be stored in a different buffered image
 */
public class Multiplication extends ArithmeticOps {

  /**
   * Concurrently applies multiplication operator on two buffered images of same dimension, where
   * each row gets processed in a different thread, making overall execution faster, leveraging
   * power of multithreaed multicore CPUs
   *
   * <p>Row extraction implementation on parent class `ArithmeticOps`
   */
  public BufferedImage operate(BufferedImage operandOne, BufferedImage operandTwo, boolean clip) {
    if (!this.isEligible(operandOne, operandTwo)) {
      return null;
    }
    ExecutorService eService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    BufferedImage sink =
        new BufferedImage(operandOne.getWidth(), operandOne.getHeight(), operandOne.getType());
    for (int i = 0; i < sink.getHeight(); i++)
      eService.execute(
          new MultiplicationWorker(
              i, this.extractRow(i, operandOne), this.extractRow(i, operandTwo), sink, clip));
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
  public BufferedImage operate(String operandOne, String operandTwo, boolean clip) {
    return this.operate(
        ImportExportImage.importImage(operandOne), ImportExportImage.importImage(operandTwo), clip);
  }
}
