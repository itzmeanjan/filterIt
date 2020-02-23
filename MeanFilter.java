import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class MeanFilter implements Filter {
    String filePath; // image to be filtered

    MeanFilter(String fp) {
        this.filePath = fp;
    }

    // calculates mean of a set of integers,
    // returns rounded value
    private int mean(int[] pxlVal) {
        int sum = 0;
        for (int i : pxlVal) {
            sum += i;
        }
        return Math.round((float) sum / (float) pxlVal.length);
    }

    // reads image content, and returns so;
    // if something goes wrong, returns null, which is to be
    // handled by caller
    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File(this.filePath));
        } catch (IOException io) {
            return null;
        }
    }

    // applies mean filter on given image & updates pixels
    // of target image
    private BufferedImage filter(BufferedImage img, int order) {
        if (img instanceof BufferedImage) {
            BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                    result.setRGB(j, i,
                            (new Color(this.mean(pxl.getNeighbouringPixelsFromImage(img, 'r', order)),
                                    this.mean(pxl.getNeighbouringPixelsFromImage(img, 'g', order)),
                                    this.mean(pxl.getNeighbouringPixelsFromImage(img, 'b', order))).getRGB()));
                }
            }
            return result;
        }
        return null;
    }

    // applies specific filter & writes filtered image into target file
    public void filterAndSave(String target, int order) {
        try {
            ImageIO.write(this.filter(this.getImage(), order), imageExtension(target), new File(target));
        } catch (IOException io) {
            System.err.println(io);
        }
    }

    // obtains name of this specific filter
    public String filterName() {
        return "Mean Filter";
    }

    // obtains file extension of image
    public String imageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}