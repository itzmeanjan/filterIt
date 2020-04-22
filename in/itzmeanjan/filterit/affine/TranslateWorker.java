package in.itzmeanjan.filterit.affine;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Each row of buffered image is processed concurrently in different
 * thread of execution, each pixel along that row gets translated into new position
 * in sink image using affine transformation mechanism ( here translation ).
 *
 * Note : If P[i, j] is one such pixel location, translation of (x, y) amount
 * will place P[i, j] in P[i + y, j + x] position in sink image.
 */
class TranslateWorker implements Runnable {

    private BufferedImage sink;
    private Color[] colors;
    private int row, x, y;

    /**
     * We'll translate pixels from P[row, i] to P[row, j], in selected row of image
     *
     * @param sink Buffered image where to store changes
     * @param colors Pixel intensities along selected row ( in form of color objects )
     * @param row Row on which we're working on now
     * @param x amount of translation along X-axis
     * @param y amount of translation along Y-axis
     */
    TranslateWorker(BufferedImage sink, Color[] colors, int row, int x, int y) {
        this.sink = sink;
        this.colors = colors;
        this.row = row;
        this.x = x;
        this.y = y;
    }

    private void translate(int posX, int posY, Color color) {
        int posXNew = posX + this.x, posYNew = posY + this.y;
        this.sink.setRGB(posXNew, posYNew, color.getRGB());
    }

    /**
     * Processing each pixel along width of image ( for
     * selected row which is under processing in this
     * thread of worker )
     */
    @Override
    public void run() {
        for (int i = 0; i < colors.length; i++) {
            this.translate(i, this.row, colors[i]);
        }
    }
}
