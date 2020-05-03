package in.itzmeanjan.filterit.segmentation;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RegionGrowing {
    private boolean isPixelValid(int width, int height, Position position) {
        return (position.getY() >= 0 && position.getY() < height)
                && (position.getX() >= 0 && position.getX() < width);
    }

    private int getIntensity(BufferedImage img, Position position) {
        return new Color(img.getRGB(position.getX(), position.getY())).getRed();
    }

    public BufferedImage segment(BufferedImage img, Position position, int relaxation) {
        if (img == null) {
            return null;
        }
        int width = img.getWidth(), height = img.getHeight();
        if (!isPixelValid(width, height, position)) {
            return null;
        }
        img = new GrayScale().grayscale(img);
        BufferedImage sink = new BufferedImage(width, height, img.getType());
        sink = ImportExportImage.setCanvas(sink, new Color(255, 255, 255));

        int targetIntensity = this.getIntensity(img, position), pixelC = width * height;
        ArrayList<Position> buffer = new ArrayList();
        buffer.add(position);
        ArrayList<Position> visited = new ArrayList();
        while (!buffer.isEmpty()) {
            Position positionUnderVisit = buffer.remove(0);
            Position[][] neighbours = positionUnderVisit.getNeighbours();
            for (Position[] i : neighbours) {
                for (Position j : i) {
                    if (isPixelValid(width, height, j)) {
                        int intensity = this.getIntensity(img, j);
                        if ((targetIntensity - relaxation) <= intensity ||
                                (targetIntensity + relaxation) >= intensity) {
                            if (!buffer.contains(j) && !visited.contains(j))
                                buffer.add(j);
                        }
                    }
                }
            }
            visited.add(positionUnderVisit);
        }

        for (Position pos : visited) {
            sink.setRGB(
                    pos.getX(), pos.getY(), new Color(0, 0, 0).getRGB()
            );
        }
        return sink;
    }

    public BufferedImage segment(String img, Position position, int relaxation) {
        return this.segment(ImportExportImage.importImage(img), position, relaxation);
    }
}
