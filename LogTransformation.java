import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Given an image ( either color or grayscaled ), we'll trasform each pixel
 * intensity value by using one logarithm based function & output image to be
 * exported into file.
 * 
 * Transformation can be controlled by changing `base` value of logarithm.
 */
class LogTransformation {

    /**
     * Computes `k` value to be used in transformation function ( implemented just
     * below ), using ( maxIntensity / ln( 1 + maxIntensity ) ) formula.
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
     * Given an instance of BufferedImage class & logarithm base value i.e. 10 or e,
     * it'll compute transformed pixel intensity values for each color component (
     * R, G or B ), which is to be stored in a new BufferedImage object, which will
     * be returned
     */
    BufferedImage transform(BufferedImage img, double base) {
        if (img == null)
            return null;
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                transformed.setRGB(j, i,
                        (new Color(this.transformPixel(base, color.getRed(), 255),
                                this.transformPixel(base, color.getGreen(), 255),
                                this.transformPixel(base, color.getBlue(), 255))).getRGB());
            }
        }
        return transformed;
    }

    /**
     * Given a source image file, from which we'll read content of image & invoke
     * method which is defined exactly above, doesn't do anything on its own. Just
     * provides another way to apply log transformation on image.
     */
    BufferedImage transform(String src, double base) {
        return this.transform(ImportExportImage.importImage(src), base);
    }

    public static void main(String[] args) {
        LogTransformation lTransformation = new LogTransformation();
        System.out.println(ImportExportImage.exportImage(lTransformation.transform("./examples/pollen.jpg", Math.E),
                "./examples/logTransformed_e.jpg"));
        System.out.println(ImportExportImage.exportImage(lTransformation.transform("./examples/pollen.jpg", 10),
                "./examples/logTransformed_10.jpg"));
        System.out.println(ImportExportImage.exportImage(lTransformation.transform("./examples/abstract.jpg", Math.E),
                "./examples/logTransformedColor_e.jpg"));
        System.out.println(ImportExportImage.exportImage(lTransformation.transform("./examples/abstract.jpg", 10),
                "./examples/logTransformedColor_10.jpg"));
    }

}