import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class MedianFilter implements Filter {
    String filePath;

    MedianFilter(String fp) {
        this.filePath = fp;
    }

    // swaps two elements of an array, indicated by their indices
    private void swap(int posI, int posJ, int[] pixels) {
        int tmp = pixels[posI];
        pixels[posI] = pixels[posJ];
        pixels[posJ] = tmp;
    }

    // sorts a set of integers using bubble sort
    // cause we'll sorting pretty small number
    // of elements, so I'm staying with this O(n^2) algo
    private void sort(int[] pxlVal) {
        for (int i = 0; i < pxlVal.length; i++) {
            for (int j = 0; j < pxlVal.length - i - 1; j++) {
                if (pxlVal[j] > pxlVal[j + 1]) {
                    swap(j, j + 1, pxlVal);
                }
            }
        }
    }

    // given a set of pixel amplitude values
    // we'll first sort them ascendically
    // and find median of those elements
    //
    // if we've odd number of elements
    // it's pretty easy to find out median
    // but for even number of elements,
    // we'll consider both size/2 & size/2 - 1,
    // indexed elements, and take their mean ( rounded )
    private int median(int[] pxlVal) {
        this.sort(pxlVal);
        if (pxlVal.length % 2 == 0) {
            return Math.round((float) (pxlVal[pxlVal.length / 2] + pxlVal[pxlVal.length / 2 - 1]) / (float) 2);
        }
        return pxlVal[pxlVal.length / 2];
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

    // applies median filter on given image & updates pixels
    // of target image
    private BufferedImage filter(BufferedImage img, int order) {
        if (img instanceof BufferedImage) {
            BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    Pixel pxl = new Pixel(img.getWidth(), img.getHeight(), i, j);
                    result.setRGB(j, i,
                            (new Color(this.median(pxl.getNeighbouringPixelsFromImage(img, 'r', order)),
                                    this.median(pxl.getNeighbouringPixelsFromImage(img, 'g', order)),
                                    this.median(pxl.getNeighbouringPixelsFromImage(img, 'b', order))).getRGB()));
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
        return "Median Filter";
    }

    // obtains file extension of image
    public String imageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}