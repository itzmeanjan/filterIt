package in.itzmeanjan.filterit.morphology;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.filter.ModeFilter;

import java.awt.image.BufferedImage;

public class Dilation {

    public BufferedImage dilate(BufferedImage img, int itr) {
        if (img == null || itr < 1) {
            return null;
        }
        for (int i = 0; i < itr; i++) {
            img = new ModeFilter().filter(img, 1);
        }
        return img;
    }

    public BufferedImage dilate(String img, int itr) {
        return this.dilate(ImportExportImage.importImage(img), itr);
    }
}
