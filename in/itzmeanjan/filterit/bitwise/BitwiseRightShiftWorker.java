package in.itzmeanjan.filterit.bitwise;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Worker class to be invoked for applying bitwise right shift operator on pixel
 * intensities along a row of source image.
 */
class BitwiseRightShiftWorker implements Runnable {
    private int row, byPlace;
    private Color[] colors;
    private BufferedImage sink;

    BitwiseRightShiftWorker(int row, int byPlace, Color[] colors, BufferedImage sink) {
        this.row = row;
        this.byPlace = byPlace;
        this.colors = colors;
        this.sink = sink;
    }

    /**
     * Given a color object stored at P[x, y] of source image, it'll compute right
     * shifted color object to be stored at P[x, y] in sink image
     */
    private Color shift(Color c) {
        return new Color(c.getRed() >> this.byPlace, c.getGreen() >> this.byPlace, c.getBlue() >> this.byPlace);
    }

    @Override
    public void run() {
        for (int i = 0; i < this.sink.getWidth(); i++)
            this.sink.setRGB(i, this.row, this.shift(colors[i]).getRGB());
    }
}