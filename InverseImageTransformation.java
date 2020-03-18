import java.awt.Color;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Takes an input image file, applies inverse image transformation on each pixel
 * of it, and produces output image, which is exported into specified file.
 */
class InverseImageTransformation {
    String filePath; // file on which transformation to be applied

    InverseImageTransformation(String fp) {
        this.filePath = fp;
    }

    /**
     * Reads image & constructs BufferedImage object, which can be used for
     * manipulating image
     */
    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File(this.filePath));
        } catch (IOException io) {
            return null;
        }
    }

    /**
     * Each pixel needs to be transformed i.e. pixel intensity value of each pixel
     * needs to be mapped to different value using some function
     * 
     * Here that transformation function is : I(x, y) = L - 1 - I(x, y), where L-1 =
     * 255 for 8-bit gray scaled image i.e. highest value that pixel intensity can
     * possibly be.
     */
    private int transformPixel(int intensity, int maxIntensity) {
        return maxIntensity - intensity;
    }

    /**
     * Each pixel of one W x H grayscale image is transformed to different value
     * using predefined function, & resulting image is exported into target file
     * which is specified while invoking this method.
     */
    ReturnVal transform(String targetPath) {
        BufferedImage img = this.getImage();
        if (img == null)
            return new ReturnVal(1, "couldn't read source image");
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                int newIntensity = this.transformPixel((new Color(img.getRGB(j, i))).getRed(), 255); // image which is
                                                                                                     // to be
                                                                                                     // transformed
                                                                                                     // needs to be
                                                                                                     // grayscaled
                transformed.setRGB(j, i, (new Color(newIntensity, newIntensity, newIntensity)).getRGB());
            }
        }
        try {
            ImageIO.write(transformed, Driver.imageExtension(targetPath), new File(targetPath));
        } catch (IOException io) {
            return new ReturnVal(1, io.toString());
        }
        return new ReturnVal(0, "success");
    }

    public static void main(String[] args) {
        InverseImageTransformation iTransformation = new InverseImageTransformation("./examples/grayscaled.jpg");
        System.out.println(iTransformation.transform("./examples/inverseTransformed.jpg"));
    }
}