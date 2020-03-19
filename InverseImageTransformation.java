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
     * Given one grayscaled / color image, it can be inversed using this method,
     * it'll treat each pixel intensity value as RGB of three different component
     * which might not have same values ( >= 0 && <= 255 )
     * 
     * And then it'll explicitly apply inverse function on each of them, which will
     * be combined for forming new color. If each color components having same
     * values, they will have same value even after inversing i.e. grayscaled image
     * stays grayscaled after inversing is done & colored image stays colored
     */
    ReturnVal transform(String targetPath) {
        BufferedImage img = this.getImage();
        if (img == null)
            return new ReturnVal(1, "couldn't read source image");
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i)); // color image can be processed using this method
                transformed.setRGB(j, i,
                        (new Color(this.transformPixel(color.getRed(), 255), this.transformPixel(color.getGreen(), 255),
                                this.transformPixel(color.getBlue(), 255))).getRGB()); // each
                                                                                       // pixel
                                                                                       // intensity
                                                                                       // value's
                                                                                       // different
                                                                                       // components
                                                                                       // need
                                                                                       // to
                                                                                       // be
                                                                                       // inversed,
                                                                                       // which
                                                                                       // are
                                                                                       // combined
                                                                                       // for
                                                                                       // forming
                                                                                       // new
                                                                                       // image
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
        InverseImageTransformation iTransformation = new InverseImageTransformation("./examples/pulmonary_abscess.jpg");
        System.out.println(iTransformation.transform("./examples/inverseTransformed.jpg"));
        iTransformation = new InverseImageTransformation("./examples/gray_sample.jpg");
        System.out.println(iTransformation.transform("./examples/inverseTransformedColor.jpg"));
    }
}