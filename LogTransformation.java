import java.awt.Color;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Given an image ( either color or grayscaled ), we'll trasform each pixel
 * intensity value by using one logarithm based function & output image to be
 * exported into file.
 * 
 * Transformation can be controlled by changing `base` value of logarithm.
 */
class LogTransformation {

    String filePath; // image to be modified

    LogTransformation(String fp) {
        this.filePath = fp;
    }

    /**
     * Reads content of image file & constructs an instance of BufferedImage class.
     */
    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File(this.filePath));
        } catch (IOException io) {
            return null;
        }
    }

    /**
     * Computes `k` value to be used in transformation function ( implemented just
     * below ), using ( maxIntensity / ln( 1+maxIntensity ) ) formula.
     */
    private double getK(double base, int maxIntensity) {
        return (double) maxIntensity / (Math.log(1 + maxIntensity) / Math.log(base));
    }

    /**
     * Applies pixel transformation function on each pixel intensity value.
     * 
     * I(x, y) = k * ln( 1 + I(x, y) ), function to be used for transformation
     * purpose. Returns rounded value.
     */
    private int transformPixel(double base, int intensity, int maxIntensity) {
        return (int) (this.getK(base, maxIntensity) * (Math.log(1 + intensity) / Math.log(base)));
    }

    /**
     * Given a target file path & logarithm base value i.e. 10 or e, it'll compute
     * transformed pixel intensity values for each color component ( R, G or B ),
     * which is to be stored in a BufferedImage, which will be finally exported into
     * target file.
     */
    ReturnVal transform(String targetPath, double base) {
        BufferedImage img = this.getImage();
        if (img == null)
            return new ReturnVal(1, "couldn't read source image");
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i)); // color image can be processed using this method
                transformed.setRGB(j, i,
                        (new Color(this.transformPixel(base, color.getRed(), 255),
                                this.transformPixel(base, color.getGreen(), 255),
                                this.transformPixel(base, color.getBlue(), 255))).getRGB()); // each
                // pixel
                // intensity
                // value's
                // different
                // components ( i.e. R, G or B )
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
        LogTransformation lTransformation = new LogTransformation("./examples/pollen.jpg");
        System.out.println(lTransformation.transform("./examples/logTransformed_e.jpg", Math.E));
        System.out.println(lTransformation.transform("./examples/logTransformed_10.jpg", 10));
        lTransformation = new LogTransformation("./examples/abstract.jpg");
        System.out.println(lTransformation.transform("./examples/logTransformedColor_e.jpg", Math.E));
        System.out.println(lTransformation.transform("./examples/logTransformedColor_10.jpg", 10));
    }

}