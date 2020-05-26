package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.segmentation.Image;

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
     * Given an image & order value, it'll concurrently apply mode filter on each row of image
     * matrix ( using convolution mechanism ) & return modified image, while not affecting original image
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
        for (int i = 0; i < img.getHeight(); i++) {
            eService.execute(
                    new ModeFilterWorker(
                            order,
                            i,
                            image,
                            result
                    )
            );
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

    /**
     * Obtains name of this specific filter
     */
    @Override
    public String filterName() {
        return "Mode Filter";
    }
}
