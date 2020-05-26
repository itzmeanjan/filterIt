package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;

class PrewittFilter implements EdgeDetection {

    /**
     * Checks whether requested order of filter can be applied or not,
     * order needs to be > 0
     *
     * @param order Order of filter to be applied
     * @return Whether order value is valid or not
     */
    @Override
    public boolean isOrderValid(int order) {
        return order > 0;
    }

    // generates a 3 x 3 horizontal prewitt kernel
    @Override
    public int[][] getHorizontalMask() {
        return new int[][]{{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
    }

    // generates a 3 x 3 vertical prewitt kernel
    @Override
    public int[][] getVerticalMask() {
        return new int[][]{{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
    }

    // applies given mask on top of pixels ( which is also 3x3 )
    // doesn't modify pixel value holder 2D array, creates a new array
    @Override
    public int[][] applyMask(int[][] mask, int[][] pixelV) {
        int[][] tmp = new int[pixelV.length][pixelV[0].length];
        for (int i = 0; i < pixelV.length; i++)
            for (int j = 0; j < pixelV[i].length; j++) tmp[i][j] = pixelV[i][j] * mask[i][j];
        return tmp;
    }

    // computes sum of all values present in a 2D array
    // which is array obtained after invoking above method
    // i.e. mask has been applied
    @Override
    public int computePartial(int[][] pixelV) {
        int partial = 0;
        for (int[] i : pixelV) for (int j : i) partial += j;
        return partial;
    }

    // given partial w.r.t. change along X &
    // partial w.r.t. change along Y, we'll compute
    // gradient, which will be normalized to a 0-255 range
    // because pixel intensity in 8-bit RGB image can't be
    // > 255 & < 0
    @Override
    public int computeGradient(int gx, int gy) {
        return ((int) Math.round(Math.sqrt(Math.pow(gx, 2) + Math.pow(gy, 2))))
                % 256; // because images are 8bit RGB
        // images, so we can't let
        // intensity be > 255, which
        // is why normalization is
        // required
    }

    @Override
    public BufferedImage filterV(BufferedImage img, int order) {
        if (img == null) {
            return null;
        }
        int[][] vMask = this.getVerticalMask();
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                result.setRGB(
                        j,
                        i,
                        (new Color(
                                this.computeGradient(
                                        0,
                                        this.computePartial(
                                                this.applyMask(
                                                        vMask, pxl.getNeighbouringPixelsFromImage(img, 'r', order)))),
                                this.computeGradient(
                                        0,
                                        this.computePartial(
                                                this.applyMask(
                                                        vMask, pxl.getNeighbouringPixelsFromImage(img, 'g', order)))),
                                this.computeGradient(
                                        0,
                                        this.computePartial(
                                                this.applyMask(
                                                        vMask, pxl.getNeighbouringPixelsFromImage(img, 'b', order)))))
                                .getRGB()));
            }
        }
        return result;
    }

    @Override
    public BufferedImage filterV(String src, int order) {
        return this.filterV(ImportExportImage.importImage(src), order);
    }

    @Override
    public BufferedImage filterH(BufferedImage img, int order) {
        if (img == null) {
            return null;
        }
        int[][] hMask = this.getHorizontalMask();
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                result.setRGB(
                        j,
                        i,
                        (new Color(
                                this.computeGradient(
                                        this.computePartial(
                                                this.applyMask(
                                                        hMask, pxl.getNeighbouringPixelsFromImage(img, 'r', order))),
                                        0),
                                this.computeGradient(
                                        this.computePartial(
                                                this.applyMask(
                                                        hMask, pxl.getNeighbouringPixelsFromImage(img, 'g', order))),
                                        0),
                                this.computeGradient(
                                        this.computePartial(
                                                this.applyMask(
                                                        hMask, pxl.getNeighbouringPixelsFromImage(img, 'b', order))),
                                        0))
                                .getRGB()));
            }
        }
        return result;
    }

    @Override
    public BufferedImage filterH(String src, int order) {
        return this.filterH(ImportExportImage.importImage(src), order);
    }

    @Override
    public BufferedImage filter(BufferedImage img, int order) {
        if (img == null) {
            return null;
        }
        int[][] hMask = this.getHorizontalMask();
        int[][] vMask = this.getVerticalMask();
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                int[][] redCompNeighbours = pxl.getNeighbouringPixelsFromImage(img, 'r', order);
                int[][] greenCompNeighbours = pxl.getNeighbouringPixelsFromImage(img, 'g', order);
                int[][] blueCompNeighbours = pxl.getNeighbouringPixelsFromImage(img, 'b', order);
                result.setRGB(
                        j,
                        i,
                        (new Color(
                                this.computeGradient(
                                        this.computePartial(this.applyMask(hMask, redCompNeighbours)),
                                        this.computePartial(this.applyMask(vMask, redCompNeighbours))),
                                this.computeGradient(
                                        this.computePartial(this.applyMask(hMask, greenCompNeighbours)),
                                        this.computePartial(this.applyMask(vMask, greenCompNeighbours))),
                                this.computeGradient(
                                        this.computePartial(this.applyMask(hMask, blueCompNeighbours)),
                                        this.computePartial(this.applyMask(vMask, blueCompNeighbours))))
                                .getRGB()));
            }
        }
        return result;
    }

    @Override
    public BufferedImage filter(String src, int order) {
        return this.filter(ImportExportImage.importImage(src), order);
    }

    @Override
    public String filterName() {
        return "Prewitt Filter";
    }
}
