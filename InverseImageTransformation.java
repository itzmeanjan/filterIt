import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Takes an input image file, applies inverse image transformation on each pixel
 * of it, and produces output image, which is exported into specified file.
 */
class InverseImageTransformation {

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
     * Given one grayscaled / color image ( buffered ), it can be inversed using
     * this method, it'll treat each pixel intensity value as RGB of three different
     * component which might not have same values ( >= 0 && <= 255 )
     * 
     * And then it'll explicitly apply inverse function on each of them, which will
     * be combined for forming new color. If each color components having same
     * intensity values, they will have same value even after inversing i.e.
     * grayscaled image stays grayscaled after inversing is done
     */
    BufferedImage transform(BufferedImage img) {
        if (img == null) {
            return null;
        }
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                transformed.setRGB(j, i,
                        (new Color(this.transformPixel(color.getRed(), 255), this.transformPixel(color.getGreen(), 255),
                                this.transformPixel(color.getBlue(), 255))).getRGB());
            }
        }
        return transformed;
    }

    BufferedImage transform(String src) {
        return this.transform(ImportExportImage.importImage(src));
    }

    public static void main(String[] args) {
        InverseImageTransformation iTransformation = new InverseImageTransformation();
        System.out.println(ImportExportImage.exportImage(iTransformation.transform("./examples/pulmonary_abscess.jpg"),
                "./examples/inverseTransformed.jpg"));
        System.out.println(ImportExportImage.exportImage(iTransformation.transform("./examples/gray_sample.jpg"),
                "./examples/inverseTransformedColor.jpg"));
    }
}