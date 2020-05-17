package in.itzmeanjan.filterit.edgedetection;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.arithmetic.Subtraction;
import in.itzmeanjan.filterit.morphology.Erosion;

import java.awt.image.BufferedImage;

public class EdgeDetectionUsingErosion {

    public BufferedImage detect(BufferedImage img) {
        if (img == null) {
            return null;
        }
        BufferedImage gray = new GrayScale().grayscale(img);
        return new Subtraction().operate(gray, new Erosion().erode(gray, 1), true);
    }

    public BufferedImage detect(String img) {
        return this.detect(ImportExportImage.importImage(img));
    }
}
