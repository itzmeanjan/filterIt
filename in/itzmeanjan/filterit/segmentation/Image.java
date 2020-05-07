package in.itzmeanjan.filterit.segmentation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Image abstraction class used for performing neighbourhood ( N8 ) ops with ease,
 * works only on grayscale image
 * <p>
 * Any pixel ( instance of Position class ) can stay in one of possible three states
 * <p>
 * 0 - Pixel is in inactive state
 * <p>
 * 1 - Pixel is in active state
 * <p>
 * 2 - Pixel is in dead state
 */
class Image {
    private final int width, height;
    private Position[][] positions;

    /**
     * Constructor to be used if you've all pixel intensities
     * available right away in form of matrix
     *
     * @param width     Width of image
     * @param height    Height of image
     * @param positions Pixel locations of image ( in form of matrix ), in number width * height
     */
    Image(int width, int height, Position[][] positions) {
        this.width = width;
        this.height = height;
        this.positions = positions;
    }

    /**
     * Constructor to be used if you don't have all pixel intensities
     * available right away, those can be updated later
     *
     * @param width  Width of image
     * @param height Height of image
     */
    Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.positions = new Position[height][width];
    }

    /**
     * Given a buffered image, it'll compute instance of Image
     * class holding same information, but this class has utility
     * methods which can help us in doing neighbourhood ops in a better fashion
     *
     * @param img Buffered image from which Image instance to be created
     * @return Instance of Image class, holding information grabbed from `img`
     */
    public static Image fromBufferedImage(BufferedImage img) {
        Image image = new Image(img.getWidth(), img.getHeight());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                image.setPosition(j, i, new Position(j, i, new Color(img.getRGB(j, i)).getRed()));
            }
        }
        return image;
    }

    /**
     * Returns pixel information at I[y][x]
     *
     * @param x X-coordinate of Pixel position
     * @param y Y-coordinate of Pixel position
     * @return Returns pixel at I[y][x]
     */
    public Position getPosition(int x, int y) {
        return this.positions[y][x];
    }

    /**
     * Sets pixel information at I[y][x]
     *
     * @param x        X-coordinate of Pixel position
     * @param y        Y-coordinate of Pixel position
     * @param position Pixel information we want to set at I[y][x]
     */
    public void setPosition(int x, int y, Position position) {
        this.positions[y][x] = position;
    }

    /**
     * Checks whether this pixel location lies within image or not
     *
     * @param x Pixel location X-coordinate
     * @param y Pixel location Y-coordinate
     * @return Whether this pixel lies within image or not
     */
    private boolean isPositionValid(int x, int y) {
        return (y >= 0 && y < height)
                && (x >= 0 && x < width);
    }

    /**
     * Obtains pixel locations present in N8
     * ( order 1 neighbourhood ) of specified pixel location
     *
     * @param position Pixel location
     * @return Set of pixels which are in N8 of pixel
     */
    private ArrayList<Position> getN8(Position position) {
        ArrayList<Position> n8 = new ArrayList<Position>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    continue; // skipping this pixel itself
                }
                if (this.isPositionValid(position.getX() + j - 1, position.getY() + i - 1)) {
                    n8.add(this.getPosition(position.getX() + j - 1, position.getY() + i - 1));
                }
            }
        }
        return n8;
    }

    /**
     * Obtains only those pixel locations ( in order 1 neighbourhood - N8 ) which are
     * in inactive state ( state 0 )
     *
     * @param position Pixel location
     * @return Set of pixels in order 1 neighbourhood of `position`, which are in *inactive* state
     */
    public ArrayList<Position> getUnexploredN8(Position position) {
        ArrayList<Position> n8 = this.getN8(position);
        n8.removeIf((pos) ->
                pos.getState() != 0);
        return n8;
    }

    /**
     * Set pixel state at I[y][x] to active ( 1 )
     *
     * @param x X-coordinate of Pixel
     * @param y Y-coordinate of Pixel
     */
    public void setActive(int x, int y) {
        this.getPosition(x, y).setState(1);
    }

    /**
     * Returns a matrix of all pixels present in image, in ordered fashion
     *
     * @return All pixels present in image
     */
    public Position[][] getPositions() {
        return positions;
    }
}
