import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class ModeFilter implements Filter {
    String filePath;

    ModeFilter(String fp) {
        this.filePath = fp;
    }

    // computes max amplitude pixel value
    // from neighborhood of a certain pixel ( inclusive )
    private int mode(int[] pxlVal) {
        int max = Integer.MIN_VALUE;
        for (int i : pxlVal) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    // reads image content, and returns so;
    // if something goes wrong, returns null, which is to be
    // handled by caller
    private BufferedImage getImage() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(this.filePath));
        } catch (IOException io) {
            img = null;
        }
        return img;
    }

    // applies mode filter on given image & updates pixels
    // of target image
    private BufferedImage filter(BufferedImage img, int order) {
        if (img instanceof BufferedImage) {
            BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                    result.setRGB(j, i,
                            (new Color(this.mode(pxl.getNeighbouringPixelsFromImage(img, 'r', order)),
                                    this.mode(pxl.getNeighbouringPixelsFromImage(img, 'g', order)),
                                    this.mode(pxl.getNeighbouringPixelsFromImage(img, 'b', order))).getRGB()));
                }
            }
            return result;
        }
        return null;
    }

    // applies specific filter & writes filtered image into target file
    public ReturnVal filterAndSave(String target, int order) {
        try {
            ImageIO.write(this.filter(this.getImage(), order), imageExtension(target), new File(target));
            return new ReturnVal(0, "success");
        } catch (IOException io) {
            return new ReturnVal(1, io.toString());
        }
    }

    // obtains name of this specific filter
    public String filterName() {
        return "Mode Filter";
    }

    // obtains file extension of image
    public String imageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}