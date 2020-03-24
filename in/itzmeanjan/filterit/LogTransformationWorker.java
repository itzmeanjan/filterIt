package in.itzmeanjan.filterit;

import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Worker class to be invoked from LogTransformation.transform(), for
 * transforming each pixel, and to be processed by threads available in thread
 * pool, which was initially defined
 */
class LogTransformationWorker implements Runnable {
    int idxI, idxJ;
    double lBase, k;
    Color color;
    BufferedImage sink;

    LogTransformationWorker(int i, int j, double lBase, double k, Color color, BufferedImage sink) {
        this.idxI = i;
        this.idxJ = j;
        this.lBase = lBase;
        this.k = k;
        this.color = color;
        this.sink = sink;
    }

    /**
     * Applies pixel transformation function on each pixel intensity value.
     * 
     * I(x, y) = k * ln( 1 + I(x, y) ), function to be used for transformation
     * purpose. Returns rounded value.
     */
    int transformPixel(double base, int intensity) {
        return (int) (this.k * (Math.log(1 + intensity) / Math.log(this.lBase)));
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
                (new Color(this.transformPixel(this.lBase, color.getRed()),
                        this.transformPixel(this.lBase, color.getGreen()),
                        this.transformPixel(this.lBase, color.getBlue()))).getRGB());
    }
}