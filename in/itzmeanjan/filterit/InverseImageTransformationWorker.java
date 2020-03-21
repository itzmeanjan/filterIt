package in.itzmeanjan.filterit;

import java.awt.image.BufferedImage;
import java.awt.Color;

class InverseImageTransformationWorker implements Runnable {

    private int idxI, idxJ;
    private Color color;
    private BufferedImage sink;

    InverseImageTransformationWorker(int i, int j, Color color, BufferedImage sink) {
        this.idxI = i;
        this.idxJ = j;
        this.color = color;
        this.sink = sink;
    }

    /**
     * Each pixel needs to be transformed i.e. pixel intensity value of each pixel
     * needs to be mapped to different value using some function
     * 
     * Here that transformation function is : I(x, y) = L - 1 - I(x, y), where L-1 =
     * 255 for 8-bit gray scaled image i.e. highest value that pixel intensity can
     * possibly be.
     */
    private int transformPixel(int intensity, int maxIntensity) {
        return maxIntensity - intensity;
    }

    @Override
    public void run() {
        this.sink.setRGB(this.idxJ, this.idxI, (new Color(this.transformPixel(color.getRed(), 255),
                this.transformPixel(color.getGreen(), 255), this.transformPixel(color.getBlue(), 255))).getRGB());
    }
}
