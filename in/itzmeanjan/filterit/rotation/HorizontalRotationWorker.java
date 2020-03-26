package in.itzmeanjan.filterit.rotation;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Main worker which rotates a given row for purpose of horizontal rotation
 * 
 * Multiple rows can be rotated concurrently by different threads
 */
class HorizontalRotationWorker implements Runnable {

    private int row;
    private BufferedImage src, sink;

    HorizontalRotationWorker(int row, BufferedImage src, BufferedImage sink) {
        this.row = row;
        this.src = src;
        this.sink = sink;
    }

    /**
     * Given two column indices for a certian row ( because we're dealing with 2D
     * arrays ), we'll swap their corresponding pixel intensity values
     * 
     * pixel intensity of image[row][i] to be put into image[row][j]
     * 
     * &&
     * 
     * pixel intensity of image[row][j] to be put into image[row][i]
     */
    private void swap(int i, int j) {
        // note that : reading intensities from source image
        Color colorI = new Color(this.src.getRGB(i, this.row));
        Color colorJ = new Color(this.src.getRGB(j, this.row));
        // but writing into sink image
        this.sink.setRGB(i, this.row, colorJ.getRGB());
        this.sink.setRGB(j, this.row, colorI.getRGB());
    }

    /**
     * We'll start two pointers at two ends of a given row i.e. one at start &
     * another at end of row, and keep swapping their pixel intensities & moving
     * them towards each other by single step ( increasing & decreasing by 1
     * respectively ) in each iteration. Stop when they meet / cross each other.
     * 
     * Which will get us one horizontally rotated row.
     */
    @Override
    public void run() {
        for (int i = 0, j = this.src.getWidth() - 1; j - i > 0; this.swap(i++, j--))
            ;
    }
}