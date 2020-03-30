package in.itzmeanjan.filterit.transform;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given an image ( either color or grayscaled ), we'll transform each pixel intensity of that image
 * using power law transformation / gamma correction function. Degree of transformation can be
 * controlled using `gamma` value.
 */
public class GammaCorrection {

  /**
   * Applies transformation function on each pixel of buffered image ( either color or grayscaled
   * image ) & returns modified image
   *
   * <p>Concurrent processing has been incorporated, using Java ExecutorService, where #-of
   * available CPU cores ( to JVM ) many working threads to be created, they'll process each pixel &
   * get free & again if any job is avaiable that's to be thrown at them ... this keeps going on
   * i.e. makes processing faster for large images
   */
  BufferedImage transform(BufferedImage img, double gamma) {
    if (img == null) {
      return null;
    }
    ExecutorService eService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
    for (int i = 0; i < transformed.getHeight(); i++) {
      for (int j = 0; j < transformed.getWidth(); j++) {
        eService.execute(
            new GammaCorrectionWorker(i, j, gamma, new Color(img.getRGB(j, i)), transformed));
      }
    }
    eService.shutdown();
    try {
      eService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException ie) {
      eService.shutdownNow();
      transformed = null;
    }
    return transformed;
  }

  /**
   * Another interface to talk to gamma correction transformation function, where you get the
   * opportunity to pass path to image file
   */
  BufferedImage transform(String src, double gamma) {
    return this.transform(ImportExportImage.importImage(src), gamma);
  }
}
