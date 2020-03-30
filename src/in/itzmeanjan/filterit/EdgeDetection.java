package in.itzmeanjan.filterit;

import java.awt.image.BufferedImage;
import in.itzmeanjan.filterit.filter.Filter;

interface EdgeDetection extends Filter {
    int[][] getHorizontalMask(); // generates horizontal kernel of 3x3 size, detects

     int[][] getVerticalMask(); // generates vertical mask of 3x3 size, helps in detecting vertial edges

     int[][] applyMask(int[][] mask, int[][] pixelV); // applies given mask on selected sub images of same size
                                                              // as of mask

     int computePartial(int[][] pixelV); // computes sum of all intensity values present in masked sub image

     int computeGradient(int gx, int gy); // computes gradient using g = (gx^2 + gy^2)^(1/2), and normalizes
                                                  // value to 0-255 range

     BufferedImage filterH(BufferedImage img, int order);

     BufferedImage filterH(String src, int order);

     BufferedImage filterV(BufferedImage img, int order);

     BufferedImage filterV(String src, int order);
}