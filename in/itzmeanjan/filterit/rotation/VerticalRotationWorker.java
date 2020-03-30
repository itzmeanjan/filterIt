package in.itzmeanjan.filterit.rotation;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Actual worker class manages reversal of a column in a given image, gets executed concurrently &
 * as we're not updating same location of image from multiple threads, there's no chance of getting
 * inconsistent view of buffered image
 */
class VerticalRotationWorker implements Runnable {

  private int col;
  private BufferedImage src, sink;

  VerticalRotationWorker(int col, BufferedImage src, BufferedImage sink) {
    this.col = col; // the column which is to be reverted
    this.src = src;
    this.sink = sink;
  }

  /**
   * Swaps two pixel intensities present along a column
   *
   * <p>image[i][col] to be put into image[j][col]
   *
   * <p>&
   *
   * <p>image[j][col] to be put in place of image[i][col]
   *
   * <p>but point to be noted is that, we're not really touching source image, rather modifications
   * are getting performed in sink image
   */
  private void swap(int i, int j) {
    // note that : reading intensities from source image
    Color colorI = new Color(this.src.getRGB(this.col, i));
    Color colorJ = new Color(this.src.getRGB(this.col, j));
    // but writing into sink image
    this.sink.setRGB(this.col, i, colorJ.getRGB());
    this.sink.setRGB(this.col, j, colorI.getRGB());
  }

  @Override
  public void run() {
    for (int i = 0, j = this.src.getHeight() - 1; j - i > 0; this.swap(i++, j--)) ;
  }
}
