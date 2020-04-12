package in.itzmeanjan.filterit.bitwise;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given two images of equal dimension, it'll compute resulting image ( concurrently i.e. each row
 * processing to be thrown at thread pool, which will pick up job from queue ) by applying bitwise
 * XOR operator on pixel intensity values for each pixel P[i, j] from both of images
 */
public class BitwiseXOR extends BitwiseOps {

  /** Concurrent implementation of bitwise XOR operator on images of equal dimensions */
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
          new BitwiseXORWorker(
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
