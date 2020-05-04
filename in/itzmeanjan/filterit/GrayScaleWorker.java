package in.itzmeanjan.filterit;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Processes each row of image concurrently
 */
class GrayScaleWorker implements Runnable {
    private BufferedImage sink;
    private int row;
    private Color[] colors;

    /**
     * Processes each row of image concurrently,
     * this worker will keep taking up jobs being thrown at it, from job queue
     *
     * @param row    Row to be processed
     * @param colors Pixel intensities across width of image in selected row
     * @param sink   Image where grayscale result being stored
     */
    GrayScaleWorker(int row, Color[] colors, BufferedImage sink) {
        this.row = row;
        this.colors = colors;
        this.sink = sink;
    }

    /**
     * Finds mean of pixel intensity at location I[x, y] & puts that into sink image
     *
     * @param x     X coordinate of Pixel position
     * @param y     Y coordinate of Pixel position
     * @param color Pixel intensity at I[x, y]
     */
    private void grayScalePixel(int x, int y, Color color) {
        int meanIntensity = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        this.sink.setRGB(x, y, new Color(
                meanIntensity,
                meanIntensity,
                meanIntensity
        ).getRGB());
    }

    @Override
    public void run() {
        for (int i = 0; i < colors.length; i++) {
            this.grayScalePixel(i, this.row, colors[i]);
        }
    }
}
