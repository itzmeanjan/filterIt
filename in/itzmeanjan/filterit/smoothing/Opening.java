package in.itzmeanjan.filterit.smoothing;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.morphology.Dilation;
import in.itzmeanjan.filterit.morphology.Erosion;

import java.awt.image.BufferedImage;

public class Opening {
    public BufferedImage smooth(BufferedImage img, int orderErosion, int orderDilation) {
        if (img == null) {
            return null;
        }
        return new Dilation().dilate(new Erosion().erode(img, orderErosion, 1), orderDilation, 1);
    }

    public BufferedImage smooth(String img, int orderErosion, int orderDilation) {
        return this.smooth(ImportExportImage.importImage(img), orderErosion, orderDilation);
    }
}
