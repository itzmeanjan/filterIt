package in.itzmeanjan.filterit.segmentation;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RegionGrowing {

    private boolean isPixelValid(int width, int height, int x, int y) {
        return (y >= 0 && y < height)
                && (x >= 0 && x < width);
    }

    /**
     * Computes segmented image following this rule,
     * <p>
     * all pixel locations having intensity within [intensity - relaxation, intensity + relaxation]
     * to be kept & remaining to be made black ( black used as background color )
     * <p>
     * For segmenting N objects requires N-run
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

        int targetIntensity = image.getPosition(x, y).getIntensity();
        BufferedImage sink = new BufferedImage(width, height, img.getType());
        sink = ImportExportImage.setCanvas(sink, new Color(0, 0, 0));

        image.setActive(x, y);
        ArrayList<Position> buffer = new ArrayList<Position>();
        buffer.add(image.getPosition(x, y));
        while (!buffer.isEmpty()) {
            Position position = buffer.remove(0);
            buffer.addAll(image.getUnexploredN8(position));
            buffer.forEach(pos -> {
                pos.setState(1);
            });
            if (position.isIntensityWithInRange(targetIntensity, relaxation)) {
                position.setState(2);
            }
        }

        for (Position[] positions : image.getPositions()) {
            for (Position pos : positions) {
                if (pos.getState() == 2) {
                    sink.setRGB(
                            pos.getX(),
                            pos.getY(),
                            new Color(pos.getIntensity(), pos.getIntensity(), pos.getIntensity()).getRGB()
                    );
                }
            }
        }

        return sink;
    }

    /**
     * Segments image starting from specified pixel location, considering intensity of init location
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
