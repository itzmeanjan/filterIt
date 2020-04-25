package in.itzmeanjan.filterit.affine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Worker class to be invoked for applying scaling factor
 * on each pixels along one selected row ( because for leveraging
 * power of concurrency, we're going to process each row of image matrix
 * concurrently, by throwing task at thread pool )
 */
class ScaleWorker implements Runnable {
    private BufferedImage sink;
    private Color[] colors;
    private double x, y;
    private int row;

    /**
     * Scales pixels present in this row of image
     *
     * @param sink   Sink image, which is being scaled, concurrently
     * @param x      Scale factor along X
     * @param y      Scale factor along Y
     * @param row    Row index in image matrix, which is under processing in this worker
     * @param colors Pixel intensities of all pixel locations along width of image, for selected row
     */
    public ScaleWorker(BufferedImage sink, double x, double y, int row, Color[] colors) {
        this.sink = sink;
        this.x = x;
        this.y = y;
        this.row = row;
        this.colors = colors;
    }

    /**
     * Scales this pixel and puts intensity in newly computed position
     * into sink image
     *
     * @param posX  X coordinate position of pixel in source image
     * @param posY  Y coordinate position of pixel in source image
     * @param color Pixel intensity at P[posX, posY]
     */
    private void scale(int posX, int posY, Color color) {
        int posXNew = (int) Math.round(posX * this.x), posYNew = (int) Math.round(posY * this.y);
        if ((posXNew >= 0 && posXNew < this.sink.getWidth()) && (posYNew >= 0 && posYNew < this.sink.getHeight())) {
            this.sink.setRGB(posXNew, posYNew, color.getRGB());
        }
    }

    /**
     * Driver method, to be called by Thread manager
     */
    @Override
    public void run() {
        for (int i = 0; i < this.sink.getWidth(); i++) {
            this.scale(i, this.row, this.colors[i]);
        }
    }
}
