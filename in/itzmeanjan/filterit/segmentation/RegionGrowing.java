package in.itzmeanjan.filterit.segmentation;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Given a buffered image ( grayscale, if not gray scaled yet,
 * to be done inside this implementation ), it'll compute
 * segmented image using a seed pixel location, applying
 * region growing algorithm
 * <p>
 * This implementation doesn't have in-built concurrency support yet
 */
public class RegionGrowing {

    /**
     * Checks whether this pixel location is inside image matrix or not
     *
     * @param width  Width of image
     * @param height height of image
     * @param x      X-coordinate of image
     * @param y      Y-coordinate of image
     * @return whether pixel lies with in image matrix or not
     */
    private boolean isPixelValid(int width, int height, int x, int y) {
        return (y >= 0 && y < height)
                && (x >= 0 && x < width);
    }

    /**
     * Computes segmented image following this rule, while
     * considering specified pixel location as seed pixel
     * <p>
     * all pixel locations having intensity within [intensity - relaxation, intensity + relaxation]
     * to be kept ( as they are ) & remaining to be made black ( black used as background color )
     * <p>
     * ! For segmenting N objects requires N-run, which is not really a good thing !
     *
     * @param img        Image to be segmented
     * @param x          X-coordinate of Pixel, from which segmentation to be started
     * @param y          Y-coordinate of Pixel, from which segmentation to be started
     * @param relaxation relaxation around initial pixel's intensity value
     * @return Segmented image
     */
    public BufferedImage segment(BufferedImage img, int x, int y, int relaxation) {
        if (img == null) {
            return null;
        }
        int width = img.getWidth(), height = img.getHeight();
        if (!isPixelValid(width, height, x, y)) {
            return null;
        }
        Image image = Image.fromBufferedImage(new GrayScale().grayscale(img));

        int targetIntensity = image.getPosition(x, y).getIntensityR();
        BufferedImage sink = new BufferedImage(width, height, img.getType());
        sink = ImportExportImage.setCanvas(sink, new Color(0, 0, 0)); // setting sink image background to black

        image.setActive(x, y); // this is our seed pixel
        ArrayList<Position> buffer = new ArrayList<Position>(); // active pixels currently, having state 1
        buffer.add(image.getPosition(x, y)); // seed pixel is first pixel which is set active
        while (!buffer.isEmpty()) {
            Position position = buffer.remove(0);
            ArrayList<Position> tmp = image.getUnexploredN8(position);
            // considering only those neighbours from N8, which are connected in terms of intensity value too
            tmp.removeIf((pos) -> !pos.isIntensityRWithInRange(targetIntensity, relaxation));
            buffer.addAll(tmp);
            buffer.forEach(pos -> {
                pos.setState(1);
            });
            if (position.isIntensityRWithInRange(targetIntensity, relaxation)) {
                position.setState(2);
            }
        }

        for (Position[] positions : image.getPositions()) {
            for (Position pos : positions) {
                if (pos.getState() == 2) {
                    sink.setRGB(
                            pos.getX(),
                            pos.getY(),
                            new Color(
                                    pos.getIntensityR(),
                                    pos.getIntensityG(),
                                    pos.getIntensityB()).getRGB()
                    );
                }
            }
        }

        return sink;
    }

    /**
     * Segments image while considering specified pixel location as seed pixel
     * <p>
     * Selection of seed pixel is very important for good segmentation result
     *
     * @param img        Image to be segmented
     * @param x          X-coordinate of Pixel, from which segmentation to be started
     * @param y          Y-coordinate of Pixel, from which segmentation to be started
     * @param relaxation relaxation around initial pixel's intensity value
     * @return Segmented image
     */
    public BufferedImage segment(String img, int x, int y, int relaxation) {
        return this.segment(ImportExportImage.importImage(img), x, y, relaxation);
    }
}
