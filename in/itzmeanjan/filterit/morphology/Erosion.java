package in.itzmeanjan.filterit.morphology;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.filter.MinFilter;

import java.awt.image.BufferedImage;

public class Erosion {

    public BufferedImage erode(BufferedImage img) {
        return new MinFilter().filter(img, 1);
    }

    public BufferedImage erode(String img) {
        return this.erode(ImportExportImage.importImage(img));
    }
}
