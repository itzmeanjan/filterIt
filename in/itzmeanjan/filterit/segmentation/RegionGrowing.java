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

    public BufferedImage segment(BufferedImage img, int x, int y, int relaxation) {
        if (img == null) {
            return null;
        }
        int width = img.getWidth(), height = img.getHeight();
        if (!isPixelValid(width, height, x, y)) {
            return null;
        }
        Image image = Image.fromBufferedImage(new GrayScale().grayscale(img));

        BufferedImage sink = new BufferedImage(width, height, img.getType());
        sink = ImportExportImage.setCanvas(sink, new Color(255, 255, 255));

        image.setActive(x, y);
        ArrayList<Position> buffer = new ArrayList<Position>();
        buffer.add(image.getPosition(x, y));
        while (!buffer.isEmpty()) {
            Position position = buffer.remove(0);
            position.setState(1);
            buffer.addAll(image.getUnexploredN8(position));
            position.setState(2);
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

    public BufferedImage segment(String img, int x, int y, int relaxation) {
        return this.segment(ImportExportImage.importImage(img), x, y, relaxation);
    }
}
