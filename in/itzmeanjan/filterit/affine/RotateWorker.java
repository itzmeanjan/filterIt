package in.itzmeanjan.filterit.affine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Performs concurrent rotation of each row of pixels, picked
 * up from source image
 */
class RotateWorker implements Runnable {

    private BufferedImage sink;
    private int row;
    private double theta;
    private Color[] colors;

    /**
     * Rotates all pixels present on this row of image
     *
     * @param sink   Target buffered image
     * @param row    Row on which current processing being done
     * @param theta  Angle of rotation in degrees
     * @param colors Pixel intensities across selected row of source image matrix
     */
    RotateWorker(BufferedImage sink, int row, double theta, Color[] colors) {
        this.sink = sink;
        this.row = row;
        this.theta = theta;
        this.colors = colors;
    }

    private void rotate(int i, int j, Color color) {
        int newI = (int) Math.round(i * Math.cos(this.theta) - j * Math.sin(this.theta)),
                newJ = (int) Math.round(i * Math.sin(this.theta) + j * Math.cos(this.theta));
        if ((newI >= 0 && newI < this.sink.getHeight()) && (newJ >= 0 && newJ < this.sink.getWidth())) {
            this.sink.setRGB(newJ, newI, color.getRGB());
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < this.colors.length; i++) {
            this.rotate(row, i, this.colors[i]);
        }
    }
}
