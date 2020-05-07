package in.itzmeanjan.filterit.segmentation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class Image {
    private final int width, height;
    private Position[][] positions;

    Image(int width, int height, Position[][] positions) {
        this.width = width;
        this.height = height;
        this.positions = positions;
    }

    Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.positions = new Position[height][width];
    }

    public static Image fromBufferedImage(BufferedImage img) {
        Image image = new Image(img.getWidth(), img.getHeight());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                image.setPosition(j, i, new Position(j, i, new Color(img.getRGB(j, i)).getRed()));
            }
        }
        return image;
    }

    public Position getPosition(int x, int y) {
        return this.positions[y][x];
    }

    public void setPosition(int x, int y, Position position) {
        this.positions[y][x] = position;
    }

    private boolean isPositionValid(int x, int y) {
        return (y >= 0 && y < height)
                && (x >= 0 && x < width);
    }

    private ArrayList<Position> getN8(Position position) {
        ArrayList<Position> n8 = new ArrayList<Position>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    continue; // skipping this pixel itself
                }
                if (this.isPositionValid(position.getX() + i - 1, position.getY() + j - 1)) {
                    n8.add(this.getPosition(position.getX() + i - 1, position.getY() + j - 1));
                }
            }
        }
        return n8;
    }

    public ArrayList<Position> getUnexploredN8(Position position) {
        ArrayList<Position> n8 = this.getN8(position);
        n8.removeIf((pos) ->
                pos.getState() != 0);
        return n8;
    }

    public void setActive(int x, int y) {
        this.getPosition(x, y).setState(1);
    }

    public void setExplored(int x, int y) {
        this.getPosition(x, y).setState(2);
    }

    public Position[][] getPositions() {
        return positions;
    }
}
