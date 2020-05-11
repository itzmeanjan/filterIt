package in.itzmeanjan.filterit.edgedetection;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.arithmetic.Subtraction;
import in.itzmeanjan.filterit.morphology.Dilation;

import java.awt.image.BufferedImage;

public class EdgeDetectionUsingDilation {
    public BufferedImage detect(BufferedImage img) {
        if (img == null) {
            return null;
        }
        return new Subtraction().operate(new Dilation().dilate(img, 1), img, true);
    }

    public BufferedImage detect(String img) {
        return this.detect(ImportExportImage.importImage(img));
    }
}
