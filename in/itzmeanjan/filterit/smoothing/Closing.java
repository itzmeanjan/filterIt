package in.itzmeanjan.filterit.smoothing;

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.morphology.Dilation;
import in.itzmeanjan.filterit.morphology.Erosion;

import java.awt.image.BufferedImage;

public class Closing {

    public BufferedImage smooth(BufferedImage img, int orderDilation, int orderErosion) {
        if (img == null) {
            return null;
        }
        return new Erosion().erode(new Dilation().dilate(img, orderDilation, 1), orderErosion, 1);
    }

    public BufferedImage smooth(String img, int orderDilation, int orderErosion) {
        return this.smooth(ImportExportImage.importImage(img), orderDilation, orderErosion);
    }
}
