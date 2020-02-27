interface EdgeDetection extends Filter {
    abstract int[][] getHorizontalMask(); // generates horizontal kernel of 3x3 size, detects

    abstract int[][] getVerticalMask(); // generates vertical mask of 3x3 size, helps in detecting vertial edges

    abstract int[][] applyMask(int[][] mask, int[][] pixelV); // applies given mask on selected sub images of same size
                                                              // as of mask

    abstract int computePartial(int[][] pixelV); // computes sum of all intensity values present in masked sub image

    abstract int computeGradient(int gx, int gy); // computes gradient using g = (gx^2 + gy^2)^(1/2), and normalizes
                                                  // value to 0-255 range

    abstract ReturnVal filterAndSaveV(String target, int order); // detects only vertical edges

    abstract ReturnVal filterAndSaveH(String target, int order); // detects only horizontal edges
}