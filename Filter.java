import java.awt.image.BufferedImage;

interface Filter {
    abstract BufferedImage filter(BufferedImage img, int order);

    abstract BufferedImage filter(String src, int order);

    abstract String filterName();
}