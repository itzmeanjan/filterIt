package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Pixel {
  public int width, height, posX, posY;

  public Pixel(int w, int h, int i, int j) {
    this.width = w;
    this.height = h;
    this.posX = i;
    this.posY = j;
  }

  private Pixel copy() {
    return new Pixel(this.width, this.height, this.posX, this.posY);
  }

  private Pixel[][] getNeighbours(int order) {
    if (order < 1) return null;
    int mask = getMaskSizeFromOrder(order);
    Pixel[][] buffer = new Pixel[mask][mask];
    for (int i = 0; i < mask; i++) {
      for (int j = 0; j < mask; j++) {
        Pixel cur = this.copy();
        cur.posX = this.posX + i - 1;
        cur.posY = this.posY + j - 1;
        buffer[i][j] = cur;
      }
    }
    return buffer;
  }

  private boolean isValid() {
    return (this.posX >= 0 && this.posX < this.height)
        && (this.posY >= 0 && this.posY < this.width);
  }

  private int getMaskSizeFromOrder(int order) {
    return 2 * order + 1;
  }

  public int[][] getNeighbouringPixelsFromImage(BufferedImage img, char colorComponent, int order) {
    if (order < 1) return null;
    int mask = getMaskSizeFromOrder(order);
    Pixel[][] neighbours = this.getNeighbours(order);
    int[][] pixelV = new int[mask][mask];
    for (int i = 0; i < mask; i++) {
      for (int j = 0; j < mask; j++) {
        if (!neighbours[i][j].isValid()) {
          pixelV[i][j] = 0;
          continue;
        }
        switch (colorComponent) {
          case 'r':
            pixelV[i][j] =
                (new Color(img.getRGB(neighbours[i][j].posY, neighbours[i][j].posX))).getRed();
            break;
          case 'g':
            pixelV[i][j] =
                (new Color(img.getRGB(neighbours[i][j].posY, neighbours[i][j].posX))).getGreen();
            break;
          case 'b':
            pixelV[i][j] =
                (new Color(img.getRGB(neighbours[i][j].posY, neighbours[i][j].posX))).getBlue();
            break;
        }
      }
    }
    return pixelV;
  }

  public String toString() {
    return "Pixel : " + this.posX + ", " + this.posY;
  }
}
