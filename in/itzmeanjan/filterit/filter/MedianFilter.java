package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.segmentation.Image;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given a buffered image / path to image file, it'll compute median of pixel intensity values in
 * neighbourhood around it ( size of neighbourhood depends upon order [ >0 ] supplied while
 * invoking filter ) for each pixel position. Processing multiple pixels can be done at a time
 * because concurrency support has already been incorporated.
 * <p>
 * This model uses a row based concurrency model i.e. each row of image matrix to be processed on thread pool
 */
public class MedianFilter implements Filter {

    /**
     * Checks whether requested order of filter can be applied or not,
     * order needs to be > 0
     *
     * @param order Order of filter to be applied
     * @return Whether order value is valid or not
     */
    @Override
    public boolean isOrderValid(int order) {
        return order > 0;
    }


    /**
     * Concurrently applies median filter on each
     * row of image matrix ( prior to this implementation,
     * was using pixel based concurrency, which was
     * slowing down whole op for very large images )
     *
     * @param img   buffered image instance, on which filter to be applied
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
        Image image = Image.fromBufferedImage(img);
        for (int i = 0; i < img.getHeight(); eService.execute(
                new MedianFilterWorker(order, i++, image, result)
        ))
            ;
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
     * Applies median filter on given buffered image, returning a new buffered image
     *
     * @param src   Image to be filtered
     * @param order Order of filtering to be applied
     * @return Filtered buffered image
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
