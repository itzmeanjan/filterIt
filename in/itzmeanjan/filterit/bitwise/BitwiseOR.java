package in.itzmeanjan.filterit.bitwise;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of bitwise OR operator, given two buffered images of equal size, it'll compute
 * bitwise OR for each pixel ( in each pixel for each color component ), and produces output
 * buffered image, without anyhow modifying original images
 */
public class BitwiseOR extends BitwiseOps {

  /**
   * Sequentially extracts each row of operand images, and concurrently processes each of them, on
   * different thread of workers. Each worker applies bitwise OR operator on pixel intensity values
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
          new BitwiseORWorker(
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
