package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.Pixel;
import in.itzmeanjan.filterit.segmentation.Image;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given a buffered image, it'll compute mean of pixel intensities for a square matrix ( odd valued
 * row & column numbers ), centered at P(x, y), for each color components ( i.e. R, G & B ), and
 * update P(x, y) 's pixel intensity values in sink image.
 *
 * <p>Concurrently processes each row of image matrix; leverage power
 * of modern multi-core CPUs
 */
public class MeanFilter implements Filter {

    /**
     * Applies mean filter on given buffered image of given order
     *
     * <p>order > 0 :: considers sub image of size ( 2*order + 1 ) x ( 2*order + 1 ), around P(x, y)
     *
     * <p>order = 0, doesn't update image at all, considers itself only
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
                    new MeanFilterWorker(
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

    /**
     * Given a source image filepath & order of mean filter to be applied; it'll first read that image
     * & then call aforementioned function on buffered image
     */
    @Override
    public BufferedImage filter(String src, int order) {
        return this.filter(ImportExportImage.importImage(src), order);
    }

    // obtains name of this specific filter
    @Override
    public String filterName() {
        return "Mean Filter";
    }
}
