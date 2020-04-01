package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Pixel;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given an image, filters only minimum intensity pixels from neighborhood of a pixel (
 * neighbourhood surely depends upon order of filtering ) i.e. lets dark pixel grow or pass through
 * filter
 */
public class MinFilter implements Filter {

  /**
   * Given a buffered image, computes min pixel intensity from neighbourhood of that pixel, and
   * replaces so in sink image. Each pixel gets processed concurrently, on different therads of
   * execution
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
              new MinFilterWorker(
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

  /**
   * Given path to image file & order of filtering ( > 0 ), we'll compute min filtered image, to be
   * returned in buffered image form, which can be processed further, if desired.
   */
  @Override
  public BufferedImage filter(String src, int order) {
    return this.filter(ImportExportImage.importImage(src), order);
  }

  /** Obtains name of this specific filter */
  @Override
  public String filterName() {
    return "Min Filter";
  }
}
