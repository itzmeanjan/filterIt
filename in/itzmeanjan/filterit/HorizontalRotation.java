package in.itzmeanjan.filterit;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Horizontally rotates each row i.e. perform side rotation on each row &
 * obtains new buffered image
 * 
 * A row, [2, 3, 4, 5] to be rotated to [5, 4, 3, 2]
 */
public class HorizontalRotation implements Rotation {
    /**
     * Goes over each row of buffered image & rotates it left to right, where each
     * row rotation to be done by one thread i.e. concurrently rows can be rotated
     * by different threads
     */
    @Override
    public BufferedImage rotate(BufferedImage img) {
        if (img == null) {
            return null;
        }
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); eService.execute(new HorizontalRotationWorker(i++, img, sink)))
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
     * Reads content of image & calls method defined exactly above
     */
    @Override
    public BufferedImage rotate(String src) {
        return this.rotate(ImportExportImage.importImage(src));
    }
}