package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Actual worker class to be running on different threads ( may be at a time for multiprocessor
 * systems ) while applying Median filter of order ( >=1 ) on a given buffered image. Processes a
 * single pixel in an image.
 */
class MedianFilterWorker implements Runnable {

  private int order;
  private Pixel pixel;
  private BufferedImage src, sink;

  MedianFilterWorker(BufferedImage src, BufferedImage sink, Pixel pixel, int order) {
    this.src = src;
    this.sink = sink;
    this.pixel = pixel;
    this.order = order;
  }

  /** Swaps two elements of an array, indicated by their indices */
  private void swap(int posI, int posJ, int[] pixels) {
    int tmp = pixels[posI];
    pixels[posI] = pixels[posJ];
    pixels[posJ] = tmp;
  }

  /**
   * Sorts a set of integers using bubble sort cause we'll sorting pretty small number of elements,
   * so I'm staying with this O(n^2) algo
   */
  private void sort(int[] pxlVal) {
    for (int i = 0; i < pxlVal.length; i++) {
      for (int j = 0; j < pxlVal.length - i - 1; j++) {
        if (pxlVal[j] > pxlVal[j + 1]) {
          swap(j, j + 1, pxlVal);
        }
      }
    }
  }

  /**
   * Given a two dimensional array of integers, we'll convert it into a single dimensional
   * representation; where value at pxlVal[i][j] will be placed at ( i * pxlVal[i].length + j ) in
   * single dimensional array
   */
  private int[] serialize(int[][] pxlVal) {
    int[] arr = new int[(int) Math.pow(pxlVal.length, 2)];
    int idx = 0;
    for (int[] i : pxlVal) for (int j : i) arr[idx++] = j;
    return arr;
  }

  /**
   * Given a set of pixel intensities we'll first serialize them into single dimensional form then
   * sort them ascendically and find median of those elements
   *
   * <p>If we've odd number of elements it's pretty easy to find out median but for even number of
   * elements, we'll consider both size/2 & size/2 - 1, indexed elements, and take their rounded
   * mean
   */
  private int median(int[][] pxlVal) {
    int[] tmp = this.serialize(pxlVal);
    this.sort(tmp);
    if (tmp.length % 2 == 0) {
      return Math.round((float) (tmp[tmp.length / 2] + tmp[tmp.length / 2 - 1]) / (float) 2);
    }
    return tmp[tmp.length / 2];
  }

  /**
   * Given a color component name i.e. one of {R, G, B}, it'll first obtain neighbourhood pixels
   * intensity values for that color component; and compute median of them, which will be set as
   * pixel intensity of P(x, y) at sink image for this color component
   *
   * <p>That means, this function to be called thrice with R, G & B as color param's value.
   */
  private int getMedianForColorComponent(char color) {
    return this.median(this.pixel.getNeighbouringPixelsFromImage(this.src, color, this.order));
  }

  @Override
  public void run() {
    this.sink.setRGB(
        this.pixel.posY,
        this.pixel.posX,
        new Color(
                this.getMedianForColorComponent('r'),
                this.getMedianForColorComponent('g'),
                this.getMedianForColorComponent('b'))
            .getRGB());
  }
}
