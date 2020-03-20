import java.awt.Color;
import java.awt.image.BufferedImage;

class ContrastStretching {
    private int minIntensity(BufferedImage img) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int tmpI = new Color(img.getRGB(j, i)).getRed();
                if (min > tmpI)
                    min = tmpI;
            }
        }
        return min;
    }

    private int maxIntensity(BufferedImage img) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int tmpI = new Color(img.getRGB(j, i)).getRed();
                if (max < tmpI)
                    max = tmpI;
            }
        }
        return max;
    }

    private int transformPixel(int minIntensity, int maxIntensity, int intensity, int maxPossibleIntensity) {
        return (int) (maxPossibleIntensity
                * ((double) (intensity - minIntensity) / (double) (maxIntensity - minIntensity)));
    }

    BufferedImage transform(BufferedImage img) {
        if (img == null) {
            return null;
        }
        img = (new GrayScale()).grayscale(img);
        int minIntensity = this.minIntensity(img);
        int maxIntensity = this.maxIntensity(img);
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                int transformedIntensity = this.transformPixel(minIntensity, maxIntensity,
                        new Color(img.getRGB(j, i)).getRed(), 255);
                transformed.setRGB(j, i,
                        (new Color(transformedIntensity, transformedIntensity, transformedIntensity)).getRGB());
            }
        }
        return transformed;
    }

    BufferedImage transform(String src) {
        return this.transform(ImportExportImage.importImage(src));
    }

    public static void main(String[] args) {
        ContrastStretching cStretching = new ContrastStretching();
        System.out.println(ImportExportImage.exportImage(cStretching.transform("./examples/texture.jpg"),
                "./examples/contrastStretched.jpg"));
    }
}