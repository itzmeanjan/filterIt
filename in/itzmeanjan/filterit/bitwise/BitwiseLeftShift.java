package in.itzmeanjan.filterit.bitwise;

import in.itzmeanjan.filterit.ImportExportImage;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Shifts each pixel intensity I[x, y] of given buffered image by applying left
 * shift operator i.e. shifting intensity value by given number of bit places.
 * Depending upon user choice transformed pixel intesities are either scaled /
 * clipped.
 */
public class BitwiseLeftShift {
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
     * Applies bitwise left shift operator on each pixel P[x, y] of source image &
     * result is stored in sink image.
     * 
     * <p>
     * Each row of image gets processed concurrently, leveraging power of modern
     * multi threaded multi core CPUs
     * 
     * <p>
     * Number of bit positions by which each pixel intensity to be shifted needs to
     * > 0 & consider not making it too high because that will eventually exceed [0,
     * 255] range i.e. 8-bit pixel intensity value, which needs to be scaled /
     * clipped depending upon user input
     */
    public BufferedImage operate(BufferedImage src, int byPlace, boolean clip) {
        if (src == null || byPlace < 0) {
            return null;
        }
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage sink = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        for (int i = 0; i < sink.getHeight(); i++)
            eService.execute(new BitwiseLeftShiftWorker(i, byPlace, extractRow(i, src), sink, clip));
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

    public BufferedImage operate(String filePath, int byPlace, boolean clip) {
        return this.operate(ImportExportImage.importImage(filePath), byPlace, clip);
    }
}