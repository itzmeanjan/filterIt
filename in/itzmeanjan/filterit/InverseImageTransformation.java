package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Takes an input image file, applies inverse image transformation on each pixel
 * of it, and produces output image, which is exported into specified file.
 */
public class InverseImageTransformation {

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
    public BufferedImage transform(BufferedImage img) {
        if (img == null) {
            return null;
        }
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                eService.execute(new InverseImageTransformationWorker(i, j, new Color(img.getRGB(j, i)), transformed));
            }
        }
        eService.shutdown();
        return transformed;
    }

    public BufferedImage transform(String src) {
        return this.transform(ImportExportImage.importImage(src));
    }

}