package in.itzmeanjan.filterit.transform;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Worker, to be invoked for processing a pixel ( i.e. applying gamma correction transformation
 * function ), to be submitted to thread pool & when a thread is available it'll get the job done.
 */
class GammaCorrectionWorker implements Runnable {

  private int idxI, idxJ;
  private double gamma;
  private Color color;
  private BufferedImage sink;

  GammaCorrectionWorker(int i, int j, double gamma, Color color, BufferedImage sink) {
    this.idxI = i;
    this.idxJ = j;
    this.gamma = gamma;
    this.color = color;
    this.sink = sink;
  }

  /**
   * Given intensity of certain pixel & max intensity value ( for 8-bit image it'll be 255 ), we'll
   * simply divide intensity by max intensity, which will give us normalized intensity value ∈ [0,
   * 1]
   */
  private double normalizeIntensity(int intensity, int maxIntensity) {
    return (double) intensity / (double) maxIntensity;
  }

  /**
   * Transforms single color component's ( i.e. R, G or B ) intensity value for a pixel, using I(x,
   * y) = 255 * e ^ ( γ * ln(I(x, y)) ), returns rounded value because pixel intensity can't be
   * float
   */
  private int transformPixel(int intensity, int maxIntensity) {
    return (int)
        (255
            * Math.pow(
                Math.E, this.gamma * Math.log(this.normalizeIntensity(intensity, maxIntensity))));
  }

  @Override
  public void run() {
    this.sink.setRGB(
        this.idxJ,
        this.idxI,
        (new Color(
                this.transformPixel(color.getRed(), 255),
                this.transformPixel(color.getGreen(), 255),
                this.transformPixel(color.getBlue(), 255)))
            .getRGB());
  }
}
