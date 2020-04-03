package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Pixel;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of mode filter i.e. replaces each pixel intensity value by its neighbouring pixel
 * intensities max value
 */
public class ModeFilter implements Filter {

  /**
   * Given an image & order value, it'll concurrently apply mode filter on each pixel ( using
   * convolution mechanism ) & return modified image, while not affecting original image
   */
  @Override
  public BufferedImage filter(BufferedImage img, int order) {
    if (img == null) {
      return null;
    }
    ExecutorService eService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0;
          j < img.getWidth();
          eService.execute(
              new ModeFilterWorker(
                  img, result, new Pixel(img.getWidth(), img.getHeight(), i, j++), order))) ;
    }
    eService.shutdown();
    try {
      // waiting for all of those workers to complete their tasks
      eService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException ie) {
      eService.shutdownNow();
      result = null;
    }
    return result;
  }

  @Override
  public BufferedImage filter(String src, int order) {
    return this.filter(ImportExportImage.importImage(src), order);
  }

  /** Obtains name of this specific filter */
  @Override
  public String filterName() {
    return "Mode Filter";
  }
}
