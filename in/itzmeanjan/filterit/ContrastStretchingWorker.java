package in.itzmeanjan.filterit;

import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Worker class to be thrown in thread pool, for concurrently modifiing pixels
 * of image
 */
class ContrastStretchingWorker implements Runnable {

    private int idxI, idxJ, minIntensity, maxIntensity;
    private Color color;
    private BufferedImage sink;

    ContrastStretchingWorker(int i, int j, int minIntensity, int maxIntensity, Color color, BufferedImage sink) {
        this.idxI = i;
        this.idxJ = j;
        this.minIntensity = minIntensity;
        this.maxIntensity = maxIntensity;
        this.color = color;
        this.sink = sink;
    }

    /**
     * We'll transform pixel intensity value using stretching function & return
     * transformed value ( >= 0 && <= 255 ), which will be put into (x, y) position
     * of modified image buffer
     * 
     * I(x, y) = round(( I(maxPossible) - I(min) ) / ( I(max) - I(min) )),
     * 
     * I(maxPossible) = 255 for 8-bit image, which is what I'm using here
     * 
     * 0 <= I(min), I(max) <= 255
     */
    private int transformPixel(int minIntensity, int maxIntensity, int intensity, int maxPossibleIntensity) {
        return (int) (maxPossibleIntensity
                * ((double) (intensity - minIntensity) / (double) (maxIntensity - minIntensity)));
    }

    @Override
    public void run() {
        int transformedIntensity = this.transformPixel(this.minIntensity, this.maxIntensity, color.getRed(), 255);
        this.sink.setRGB(this.idxJ, this.idxI,
                (new Color(transformedIntensity, transformedIntensity, transformedIntensity)).getRGB());
    }
}