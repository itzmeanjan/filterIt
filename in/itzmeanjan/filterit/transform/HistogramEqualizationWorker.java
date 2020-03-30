package in.itzmeanjan.filterit.transform;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Transforms single pixel of buffered image using histogram equalization method & stores it into
 * output image ( in supplied position )
 *
 * <p>Multiple pixels can be modified at same time, while each of them running in a different thread
 * of execution, concurrent
 */
class HistogramEqualizationWorker implements Runnable {
  private int idxI, idxJ;
  private Color color;
  private double minCDF;
  private double[] cdfs;
  private BufferedImage sink;

  HistogramEqualizationWorker(
      int i, int j, double minCDF, double[] cdfs, Color color, BufferedImage sink) {
    this.idxI = i;
    this.idxJ = j;
    this.minCDF = minCDF;
    this.cdfs = cdfs;
    this.color = color;
    this.sink = sink;
  }

  /**
   * Computes transformed pixel intensity value using Histrogram Equalization function
   *
   * <p>I(x, y) = round( 255 * (CDF - minCDF) / (1 - minCDF) ), where CDF for I(x, y) is already
   * computed & cached
   */
  private int transformPixel(double minCDF, double cdf, int maxIntensity) {
    return (int) Math.round(((cdf - minCDF) / (1.0 - minCDF)) * maxIntensity);
  }

  /**
   * Pixel transformation using histogram equalization function, to be invoked automatically by
   * thread, as soon as task is submitted & worker thread is available in thread pool
   */
  @Override
  public void run() {
    int transformedIntensity =
        this.transformPixel(this.minCDF, this.cdfs[this.color.getRed()], 255);
    this.sink.setRGB(
        this.idxJ,
        this.idxI,
        (new Color(transformedIntensity, transformedIntensity, transformedIntensity)).getRGB());
  }
}
