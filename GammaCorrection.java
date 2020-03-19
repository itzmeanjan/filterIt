import java.awt.Color;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Given an image ( either color or grayscaled ), we'll transform each pixel
 * intensity of that image using power law transformation / gamma correction
 * function. Transformation can be controlled using `gamma` value.
 */
class GammaCorrection {

    String filePath;

    GammaCorrection(String fp) {
        this.filePath = fp;
    }

    /**
     * Reads image from given file, and obtains an instance of BufferedImage class
     */
    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File(this.filePath));
        } catch (IOException io) {
            return null;
        }
    }

    /**
     * Given intensity of certain pixel & max intensity value ( for 8-bit image
     * it'll be 255 ), we'll simply divide intensity by max intensity, which will
     * give us normalized intensity value ∈ [0, 1]
     */
    private double normalizeIntensity(int intensity, int maxIntensity) {
        return (double) intensity / (double) maxIntensity;
    }

    /**
     * Transforms single color component's ( i.e. R, G or B ) intensity value for a
     * pixel, using I(x, y) = 255 * e ^ ( γ * ln(I(x, y)) ), returns rounded value
     * because pixel intensity can't be floating point value
     */
    private int transformPixel(double gamma, int intensity, int maxIntensity) {
        return (int) (255 * Math.pow(Math.E, gamma * Math.log(this.normalizeIntensity(intensity, maxIntensity))));
    }

    /**
     * Given a target file path, where output image to be exported & γ value, which
     * will control degree of transformation of any pixel, we'll compute output
     * image i.e. transformed image
     */
    ReturnVal transform(String targetPath, double gamma) {
        BufferedImage img = this.getImage();
        if (img == null)
            return new ReturnVal(1, "couldn't read source image");
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i)); // color image can be processed using this method
                transformed.setRGB(j, i,
                        (new Color(this.transformPixel(gamma, color.getRed(), 255),
                                this.transformPixel(gamma, color.getGreen(), 255),
                                this.transformPixel(gamma, color.getBlue(), 255))).getRGB()); // each
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
        GammaCorrection gCorrection = new GammaCorrection("./examples/pollen.jpg");
        System.out.println(gCorrection.transform("./examples/gammaCorrected_1_2.jpg", 1.0 / 2.0));
        System.out.println(gCorrection.transform("./examples/gammaCorrected_1_3.jpg", 1.0 / 3.0));
        System.out.println(gCorrection.transform("./examples/gammaCorrected_2.jpg", 2.0));
        System.out.println(gCorrection.transform("./examples/gammaCorrected_3.jpg", 3.0));
        gCorrection = new GammaCorrection("./examples/gray_sample.jpg");
        System.out.println(gCorrection.transform("./examples/gammaCorrectedColor_1_2.jpg", 1.0 / 2.0));
        System.out.println(gCorrection.transform("./examples/gammaCorrectedColor_1_3.jpg", 1.0 / 3.0));
        System.out.println(gCorrection.transform("./examples/gammaCorrectedColor_2.jpg", 2.0));
        System.out.println(gCorrection.transform("./examples/gammaCorrectedColor_3.jpg", 3.0));
    }

}