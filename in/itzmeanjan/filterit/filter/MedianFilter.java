package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Pixel;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given a buffered image / path to image file, it'll compute median of pixel intensity values in
 * neighbourhood around it ( size of neighbourhood depends upon order ( ]>=1 ) supplied while
 * invoking filter ) for each pixel position. Processing multiple pixels can be done at a time
 * because concurency support has already been incorporated incorporated.
 */
public class MedianFilter implements Filter {

  /**
   * Concurrently applies median filter on a given buffered image & returns updated image
   *
   * @param img buffered image instance, on which filter to be applied
   * @param order > = 1, order of filter to be applied, decides size of mask for convolution
   * @return modified buffered image, not the supplied one
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
              new MedianFilterWorker(
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
   * Just another way to talk to median filter, when path to image file is provided, this
   * implementation to invoked
   *
   * @param src path to image file
   * @param order order of filter to be applied ( >= 1 )
   * @return modified i.e. filtered image
   */
  @Override
  public BufferedImage filter(String src, int order) {
    return this.filter(ImportExportImage.importImage(src), order);
  }

  /**
   * Returns name of this filter
   *
   * @return name of filter
   */
  @Override
  public String filterName() {
    return "Median Filter";
  }
}
