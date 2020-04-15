package in.itzmeanjan.filterit.bitwise;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Given one buffered image / path to image file & number of bit positions by
 * which pixel intensity to be shifted ( rightwards ), it'll compute transformed
 * image by picking each row up & applying bitwise right shift operator on each
 * pixel intensity value.
 */
public class BitwiseRightShift {

    /**
     * Given a buffered image & row index, it'll pick up pixel intensities from that
     * row
     */
    private Color[] extractRow(int row, BufferedImage img) {
        Color[] colors = new Color[img.getWidth()];
        for (int i = 0; i < img.getWidth(); colors[i] = new Color(img.getRGB(i++, row)))
            ;
        return colors;
    }

    /**
     * Applies bitwise right shift operator on each pixel P[x, y] of source image &
     * result being stored in sink image.
     * 
     * <p>
     * Each row of image gets processed concurrently, leveraging power of modern
     * multi threaded multi core CPUs
     * 
     * <p>
     * Number of bit positions by which each pixel intensity to be shifted needs to
     * be positive integer & consider not making it greater than 8, because we're
     * working with 24-bit images ( where each color component is represented using
     * 8-bit integer )
     */
    public BufferedImage operate(BufferedImage src, int byPlace) {
        if (src == null || byPlace < 0) {
            return null;
        }
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage sink = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        for (int i = 0; i < sink.getHeight(); i++)
            eService.execute(new BitwiseRightShiftWorker(i, byPlace, extractRow(i, src), sink));
        eService.shutdown();
        try {
            // waiting for all of those workers to complete their tasks
            eService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ie) {
            eService.shutdownNow();
            sink = null;
        }
        return sink;
    }

    public BufferedImage operate(String filePath, int byPlace) {
        return this.operate(ImportExportImage.importImage(filePath), byPlace);
    }
}