package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;

class SobelFilter implements EdgeDetection {

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

    @Override
    public int[][] getHorizontalMask() {
        return new int[][]{{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    }

    @Override
    public int[][] getVerticalMask() {
        return new int[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    }

    @Override
    public int[][] applyMask(int[][] mask, int[][] pixelV) {
        int[][] tmp = new int[pixelV.length][pixelV[0].length];
        for (int i = 0; i < pixelV.length; i++)
            for (int j = 0; j < pixelV[i].length; j++) tmp[i][j] = pixelV[i][j] * mask[i][j];
        return tmp;
    }

    @Override
    public int computePartial(int[][] pixelV) {
        int partial = 0;
        for (int[] i : pixelV) for (int j : i) partial += j;
        return partial;
    }

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
                                        this.computePartial(
                                                this.applyMask(
                                                        vMask, pxl.getNeighbouringPixelsFromImage(img, 'r', order))),
                                        0),
                                this.computeGradient(
                                        this.computePartial(
                                                this.applyMask(
                                                        vMask, pxl.getNeighbouringPixelsFromImage(img, 'g', order))),
                                        0),
                                this.computeGradient(
                                        this.computePartial(
                                                this.applyMask(
                                                        vMask, pxl.getNeighbouringPixelsFromImage(img, 'b', order))),
                                        0))
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
        return "Sobel Filter";
    }
}
