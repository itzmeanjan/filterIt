package in.itzmeanjan.filterit;

import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Worker to be invoked by various threads ( due to concurrent execution ) for
 * modifying pixels using inverse logarithmic transformation, which will behave
 * same as with gamma value < 1, in case of GammaTransformation, making image
 * darker
 */
class InverseLogTransformationWorker extends LogTransformationWorker {
    InverseLogTransformationWorker(int i, int j, double lBase, double k, Color color, BufferedImage sink) {
        super(i, j, lBase, k, color, sink);
    }

    /**
     * Transforms pixel intensity value using this transformation function
     * 
     * I(x, y) = e^(I(x, y) / k) - 1
     * 
     * where k = I(max) / log( I(max) + 1 ), which eventually makes image darker
     */
    @Override
    int transformPixel(double base, int intensity) {
        return (int) (Math.exp(((double) intensity) / this.k) - 1);
    }

    @Override
    public void run() {
        this.sink.setRGB(this.idxJ, this.idxI,
                (new Color(this.transformPixel(this.lBase, color.getRed()),
                        this.transformPixel(this.lBase, color.getGreen()),
                        this.transformPixel(this.lBase, color.getBlue()))).getRGB());
    }
}