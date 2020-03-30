package in.itzmeanjan.filterit.rotation;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import in.itzmeanjan.filterit.ImportExportImage;

/**
 * Vertically rotates each column of given buffered image, returns a new
 * buffered image
 * 
 * Let's assume we've a column vector [1, 2, 3, 4, 5] of size 5x1, then its
 * rotated i.e. reversed form will be like [5, 4, 3, 2, 1] which is also 5x1
 */
public class VerticalRotation implements Rotation {
    /**
     * Given a buffered image we'll reverse each column and they will be no doubt
     * done concurrently, leveraging power of modern multicore processors
     * 
     * Reversing each column is what we call vertically rotating image i.e. rotating
     * image across its Y-axis 180°
     */
    @Override
    public BufferedImage rotate(BufferedImage img) {
        if (img == null) {
            return null;
        }
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getWidth(); eService.execute(new VerticalRotationWorker(i++, img, sink)))
            ;
        eService.shutdown();
        try {
            eService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ie) {
            eService.shutdownNow();
            sink = null;
        }
        return sink;
    }

    /**
     * Given path to image file, it'll be read & rotated, where actual vertical
     * rotation implementation is present in method just above
     */
    @Override
    public BufferedImage rotate(String src) {
        return this.rotate(ImportExportImage.importImage(src));
    }
}