package in.itzmeanjan.filterit;

import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Worker class to be invoked from LogTransformation.transform(), for
 * transforming each pixel, and to be processed by threads available in thread
 * pool, which was initially defined
 */
class LogTransformationWorker implements Runnable {
    private int idxI, idxJ;
    private double lBase;
    private Color color;
    private BufferedImage sink;

    LogTransformationWorker(int i, int j, double lBase, Color color, BufferedImage sink) {
        this.idxI = i;
        this.idxJ = j;
        this.lBase = lBase;
        this.color = color;
        this.sink = sink;
    }

    /**
     * Computes `k` value to be used in transformation function ( implemented just
     * below ), using ( maxIntensity / ln( 1 + maxIntensity ) ) formula.
     */
    private double getK(double base, int maxIntensity) {
        return (double) maxIntensity / (Math.log(1 + maxIntensity) / Math.log(this.lBase));
    }

    /**
     * Applies pixel transformation function on each pixel intensity value.
     * 
     * I(x, y) = k * ln( 1 + I(x, y) ), function to be used for transformation
     * purpose. Returns rounded value.
     */
    private int transformPixel(double base, int intensity, int maxIntensity) {
        return (int) (this.getK(base, maxIntensity) * (Math.log(1 + intensity) / Math.log(this.lBase)));
    }

    /**
     * Modifies pixel at given position of final image, using transformation
     * functions defined just above
     * 
     * This function is designed to work concurrently i.e. different threads might
     * execute this same function at same time but modifying different pixel
     * locations
     */
    @Override
    public void run() {
        this.sink.setRGB(this.idxJ, this.idxI,
                (new Color(this.transformPixel(this.lBase, color.getRed(), 255),
                        this.transformPixel(this.lBase, color.getGreen(), 255),
                        this.transformPixel(this.lBase, color.getBlue(), 255))).getRGB());
    }
}