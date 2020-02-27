import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class PrewittFilter implements EdgeDetection {
    String filePath; // image to be filtered

    PrewittFilter(String fp) {
        this.filePath = fp;
    }

    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File(this.filePath));
        } catch (IOException io) {
            return null;
        }
    }

    // generates a 3 x 3 horizontal prewitt kernel
    public int[][] getHorizontalMask() {
        return new int[][] { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
    }

    // generates a 3 x 3 vertical prewitt kernel
    public int[][] getVerticalMask() {
        return new int[][] { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
    }

    // applies given mask on top of pixels ( which is also 3x3 )
    // doesn't modify pixel value holder 2D array, creates a new array
    public int[][] applyMask(int[][] mask, int[][] pixelV) {
        int[][] tmp = new int[pixelV.length][pixelV[0].length];
        for (int i = 0; i < pixelV.length; i++)
            for (int j = 0; j < pixelV[i].length; j++)
                tmp[i][j] = pixelV[i][j] * mask[i][j];
        return tmp;
    }

    // computes sum of all values present in a 2D array
    // which is array obtained after invoking above method
    // i.e. mask has been applied
    public int computePartial(int[][] pixelV) {
        int partial = 0;
        for (int[] i : pixelV)
            for (int j : i)
                partial += j;
        return partial;
    }

    // given partial w.r.t. change along X &
    // partial w.r.t. change along Y, we'll compute
    // gradient, which will be normalized to a 0-255 range
    // because pixel intensity in 8-bit RGB image can't be
    // > 255 & < 0
    public int computeGradient(int gx, int gy) {
        return ((int) Math.round(Math.sqrt(Math.pow(gx, 2) + Math.pow(gy, 2)))) % 256; // because images are 8bit RGB
                                                                                       // images, so we can't let
                                                                                       // intensity be > 255, which
                                                                                       // is why normalization is
                                                                                       // required
    }

    private BufferedImage filterV(BufferedImage img, int order) {
        if (img instanceof BufferedImage) {
            int[][] vMask = this.getVerticalMask();
            BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                    result.setRGB(j, i,
                            (new Color(
                                    this.computeGradient(0,
                                            this.computePartial(this.applyMask(vMask,
                                                    pxl.getNeighbouringPixelsFromImage(img, 'r', order)))),
                                    this.computeGradient(0,
                                            this.computePartial(this.applyMask(vMask,
                                                    pxl.getNeighbouringPixelsFromImage(img, 'g', order)))),
                                    this.computeGradient(0, this.computePartial(this.applyMask(vMask,
                                            pxl.getNeighbouringPixelsFromImage(img, 'b', order))))).getRGB()));
                }
            }
            return result;
        }
        return null;
    }

    public ReturnVal filterAndSaveV(String target, int order) {
        try {
            if (order != 1)
                throw new Exception("Bad input : order must be 1 for PrewittFilter");
            ImageIO.write(this.filterV(this.getImage(), order), imageExtension(target), new File(target));
            return new ReturnVal(0, "success");
        } catch (IOException io) {
            return new ReturnVal(1, io.toString());
        } catch (Exception e) {
            return new ReturnVal(1, e.toString());
        }
    }

    private BufferedImage filterH(BufferedImage img, int order) {
        if (img instanceof BufferedImage) {
            int[][] hMask = this.getHorizontalMask();
            BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                    result.setRGB(j, i, (new Color(
                            this.computeGradient(
                                    this.computePartial(
                                            this.applyMask(hMask, pxl.getNeighbouringPixelsFromImage(img, 'r', order))),
                                    0),
                            this.computeGradient(
                                    this.computePartial(
                                            this.applyMask(hMask, pxl.getNeighbouringPixelsFromImage(img, 'g', order))),
                                    0),
                            this.computeGradient(
                                    this.computePartial(
                                            this.applyMask(hMask, pxl.getNeighbouringPixelsFromImage(img, 'b', order))),
                                    0)).getRGB()));
                }
            }
            return result;
        }
        return null;
    }

    public ReturnVal filterAndSaveH(String target, int order) {
        try {
            if (order != 1)
                throw new Exception("Bad input : order must be 1 for PrewittFilter");
            ImageIO.write(this.filterH(this.getImage(), order), imageExtension(target), new File(target));
            return new ReturnVal(0, "success");
        } catch (IOException io) {
            return new ReturnVal(1, io.toString());
        } catch (Exception e) {
            return new ReturnVal(1, e.toString());
        }
    }

    private BufferedImage filter(BufferedImage img, int order) {
        if (img instanceof BufferedImage) {
            int[][] hMask = this.getHorizontalMask();
            int[][] vMask = this.getVerticalMask();
            BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                    int[][] redCompNeighbours = pxl.getNeighbouringPixelsFromImage(img, 'r', order);
                    int[][] greenCompNeighbours = pxl.getNeighbouringPixelsFromImage(img, 'g', order);
                    int[][] blueCompNeighbours = pxl.getNeighbouringPixelsFromImage(img, 'b', order);
                    result.setRGB(j, i, (new Color(
                            this.computeGradient(this.computePartial(this.applyMask(hMask, redCompNeighbours)),
                                    this.computePartial(this.applyMask(vMask, redCompNeighbours))),
                            this.computeGradient(this.computePartial(this.applyMask(hMask, greenCompNeighbours)),
                                    this.computePartial(this.applyMask(vMask, greenCompNeighbours))),
                            this.computeGradient(this.computePartial(this.applyMask(hMask, blueCompNeighbours)),
                                    this.computePartial(this.applyMask(vMask, blueCompNeighbours)))).getRGB()));
                }
            }
            return result;
        }
        return null;
    }

    @Override
    public ReturnVal filterAndSave(String target, int order) {
        try {
            if (order != 1)
                throw new Exception("Bad input : order must be 1 for PrewittFilter");
            ImageIO.write(this.filter(this.getImage(), order), imageExtension(target), new File(target));
            return new ReturnVal(0, "success");
        } catch (IOException io) {
            return new ReturnVal(1, io.toString());
        } catch (Exception e) {
            return new ReturnVal(1, e.toString());
        }
    }

    @Override
    public String filterName() {
        return "Prewitt Filter";
    }

    @Override
    public String imageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void main(String[] args) {
        SobelFilter sfFilter = new SobelFilter("./examples/sample.jpg");
        System.out.println(sfFilter.filterAndSave("./prewitt.jpg", 1));
    }
}