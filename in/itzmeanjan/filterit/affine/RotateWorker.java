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
        this.theta = Math.toRadians(theta);
        this.colors = colors;
    }

    /**
     * Obtains new pixel location in sink image
     * where `color` to be placed using following
     * formula
     * <p>
     * P[i, j] = P[ round( i * cos θ - j * sin θ ), round( i * sin θ + j * cos θ )]
     *
     * @param i     Pixel position indicating row
     * @param j     Pixel position indicating column
     * @param color Pixel intensity at location (i, j)
     */
    private void rotate(int i, int j, Color color) {
        int newI = (int) Math.round(i * Math.cos(this.theta) - j * Math.sin(this.theta)),
                newJ = (int) Math.round(i * Math.sin(this.theta) + j * Math.cos(this.theta));
        if ((newI >= 0 && newI < this.sink.getHeight()) && (newJ >= 0 && newJ < this.sink.getWidth())) {
            this.sink.setRGB(newJ, newI, color.getRGB());
        }
    }

    /**
     * Iterates over all pixel positions in row & rotates them
     * by finding new location in sink image
     */
    @Override
    public void run() {
        for (int i = 0; i < this.colors.length; i++) {
            this.rotate(row, i, this.colors[i]);
        }
    }
}
