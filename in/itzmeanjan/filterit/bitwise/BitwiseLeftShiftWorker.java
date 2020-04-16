package in.itzmeanjan.filterit.bitwise;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Worker class to be invoked for applying bitwise left shift operator on pixel
 * intensities along a row of source image matrix
 */
class BitwiseLeftShiftWorker implements Runnable {
    private int row, byPlace;
    private Color[] colors;
    private BufferedImage sink;
    private boolean clip;

    BitwiseLeftShiftWorker(int row, int byPlace, Color[] colors, BufferedImage sink, boolean clip) {
        this.row = row;
        this.byPlace = byPlace;
        this.colors = colors;
        this.sink = sink;
        this.clip = clip;
    }

    /**
     * Scaling pixel intensity down to range 0-255 ( working with 24-bit three
     * component RGB images ), by applying modulas operator on pixel intensity
     *
     * <p>
     * I(x, y) = I(x, y) % 256
     */
    private int scaleIntensity(int intensity) {
        return Math.abs(intensity) % 256;
    }

    /**
     * Clipping values < 0 to 0 & > 255 to 255, and not touching anyother value
     * already in range [0, 255], allows us to keep pixel intensities in range
     */
    private int clipIntensity(int intensity) {
        return intensity < 0 ? 0 : (intensity > 255 ? 255 : intensity);
    }

    /**
     * Given a color object stored at P[x, y] of source image, it'll compute left
     * shifted color object to be stored at P[x, y] in sink image
     */
    private Color shift(Color c) {
        return this.clip ? new Color(this.clipIntensity(c.getRed() << this.byPlace),
                this.clipIntensity(c.getGreen() << this.byPlace), this.clipIntensity(c.getBlue() << this.byPlace))
                : new Color(this.scaleIntensity(c.getRed() << this.byPlace),
                        this.scaleIntensity(c.getGreen() << this.byPlace),
                        this.scaleIntensity(c.getBlue() << this.byPlace));
    }

    @Override
    public void run() {
        for (int i = 0; i < this.sink.getWidth(); i++)
            this.sink.setRGB(i, this.row, this.shift(colors[i]).getRGB());
    }
}