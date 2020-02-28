import java.awt.Color;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class GrayScale {
    String filePath; // image to be grayscaled

    GrayScale(String fp) {
        this.filePath = fp;
    }

    // reads image file content into a BufferedImage object
    private BufferedImage getImage() {
        try {
            return ImageIO.read(new File(this.filePath));
        } catch (IOException io) {
            return null;
        }
    }

    private String imageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    // grayscales a given image & writes updated image into target file
    //
    // for grayscaling, an image we'll need
    // same value ( 8-bit here i.e. 0 - 255 )
    // for three color components i.e. R, G & B
    // so we'll simply take average of existing color
    // intensities & put them as R, G & B component value
    // in each pixel location
    ReturnVal grayscale(String targetFile) {
        BufferedImage orig = this.getImage();
        if (orig == null)
            return new ReturnVal(1, "Couldn't read source image");
        BufferedImage grayscaled = new BufferedImage(orig.getWidth(), orig.getHeight(), orig.getType());
        for (int i = 0; i < grayscaled.getHeight(); i++) {
            for (int j = 0; j < grayscaled.getWidth(); j++) {
                Color color = new Color(orig.getRGB(j, i));
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                grayscaled.setRGB(j, i, (new Color(avg, avg, avg)).getRGB());
            }
        }
        try {
            ImageIO.write(grayscaled, imageExtension(targetFile), new File(targetFile));
            return new ReturnVal(0, "sucess");
        } catch (IOException io) {
            return new ReturnVal(1, io.toString());
        }
    }

}