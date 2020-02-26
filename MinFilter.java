import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// filters only minimum intensity
// pixels from neighborhood of a pixel
// i.e. lets dark pixel grow or pass through filter
class MinFilter implements Filter {
    String filePath;

    MinFilter(String fp) {
        this.filePath = fp;
    }

    // computes min intensity pixel value
    // from neighborhood of a certain pixel ( inclusive )
    private int min(int[][] pxlVal) {
        int min = Integer.MAX_VALUE;
        for (int[] i : pxlVal)
            for (int j : i)
                if (j < min)
                    min = j;
        return min;
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
                            (new Color(this.min(pxl.getNeighbouringPixelsFromImage(img, 'r', order)),
                                    this.min(pxl.getNeighbouringPixelsFromImage(img, 'g', order)),
                                    this.min(pxl.getNeighbouringPixelsFromImage(img, 'b', order))).getRGB()));
                }
            }
            return result;
        }
        return null;
    }

    // applies specific filter & writes filtered image into target file
    public ReturnVal filterAndSave(String target, int order) {
        try {
            if (order < 1)
                throw new Exception("Bad input : order must be > 0");
            ImageIO.write(this.filter(this.getImage(), order), imageExtension(target), new File(target));
            return new ReturnVal(0, "success");
        } catch (IOException io) {
            return new ReturnVal(1, io.toString());
        } catch (Exception e) {
            return new ReturnVal(1, e.toString());
        }
    }

    // obtains name of this specific filter
    public String filterName() {
        return "Min Filter";
    }

    // obtains file extension of image
    public String imageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}