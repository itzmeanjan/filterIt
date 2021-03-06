package in.itzmeanjan.filterit.transform;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given a buffered image, applies inverse image transformation function on each pixel of it, and
 * produces output buffered image
 */
public class InverseImageTransformation {

  /**
   * Given one grayscaled / color image ( buffered ), it can be inversed using this method, it'll
   * treat each pixel intensity value as RGB of three different component which might not have same
   * values ( >= 0 && <= 255 )
   *
   * <p>And then it'll explicitly apply inverse function on each of them, which will be combined for
   * forming new color. If each color components having same intensity values, they will have same
   * value even after inversing i.e. grayscaled image stays grayscaled after inversing is done
   *
   * <p>Now incorporated concurrent processing ( using ExecutorService ), starting with #-of threads
   * = #-of cores available to JVM while running this method
   */
  public BufferedImage transform(BufferedImage img) {
    if (img == null) {
      return null;
    }
    ExecutorService eService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
    for (int i = 0; i < transformed.getHeight(); i++) {
      for (int j = 0; j < transformed.getWidth(); j++) {
        // submitting task, which will be allocated to some available thread ( among
        // those many created at starting )
        eService.execute(
            new InverseImageTransformationWorker(i, j, new Color(img.getRGB(j, i)), transformed));
      }
    }
    eService.shutdown();
    try {
      // waiting to complete all running workers
      eService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException ie) {
      eService.shutdownNow();
      transformed = null;
    }
    return transformed;
  }

  /**
   * If you pass path to image, it'll read image into a buffer & pass that to actual worker method
   * which is defined just above
   */
  public BufferedImage transform(String src) {
    return this.transform(ImportExportImage.importImage(src));
  }
}
