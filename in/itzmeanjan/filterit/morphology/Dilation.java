package in.itzmeanjan.filterit.morphology;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.filter.ModeFilter;

import java.awt.image.BufferedImage;

public class Dilation {

    public BufferedImage dilate(BufferedImage img) {
        return new ModeFilter().filter(img, 1);
    }

    public BufferedImage dilate(String src) {
        return this.dilate(ImportExportImage.importImage(src));
    }
}
