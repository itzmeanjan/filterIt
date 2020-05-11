package in.itzmeanjan.filterit.morphology;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.filter.ModeFilter;

import java.awt.image.BufferedImage;

public class Dilation {

    public BufferedImage dilate(BufferedImage img, int itr) {
        if (img == null || itr < 1) {
            return null;
        }
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        sink.setData(img.getData());
        for (int i = 0; i < itr; i++) {
            sink = new ModeFilter().filter(sink, 1);
        }
        return sink;
    }

    public BufferedImage dilate(String img, int itr) {
        return this.dilate(ImportExportImage.importImage(img), itr);
    }
}
