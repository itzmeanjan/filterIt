package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.segmentation.Image;
import in.itzmeanjan.filterit.segmentation.Position;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Concurrently applies mean filter on a row of pixels in an image, I
 */
class MeanFilterWorker implements Runnable {

    private int order, row;
    private Image src;
    private BufferedImage sink;

    MeanFilterWorker(int order, int row, Image src, BufferedImage sink) {
        this.order = order;
        this.row = row;
        this.src = src;
        this.sink = sink;
    }

    /**
     * Calculates mean of a set of integers belonging to [0, 255] range,
     * returns rounded integer result ( which is nothing but pixel intensity at I[x, y] )
     */
    private int mean(int[] pxlVal) {
        int sum = 0;
        for (int i : pxlVal) {
            sum += i;
        }
        return Math.round((float) sum / (float) pxlVal.length);
    }

    /**
     * Extracts red color intensities from pixels, present in given neighbourhood
     *
     * @param positionArrayList Pixel information from neighbourhood of a pixel
     * @return Set of red color intensities from that neighbourhood
     */
    private int[] getRedIntensities(ArrayList<Position> positionArrayList) {
        int[] intensities = new int[positionArrayList.size()];
        for (int i = 0; i < intensities.length; i++) {
            intensities[i] = positionArrayList.get(i).getIntensityR();
        }
        return intensities;
    }

    /**
     * Extracts green color intensities from pixels, present in given neighbourhood
     *
     * @param positionArrayList Pixel information from neighbourhood of a pixel
     * @return Set of green color intensities from that neighbourhood
     */
    private int[] getGreenIntensities(ArrayList<Position> positionArrayList) {
        int[] intensities = new int[positionArrayList.size()];
        for (int i = 0; i < intensities.length; i++) {
            intensities[i] = positionArrayList.get(i).getIntensityG();
        }
        return intensities;
    }

    /**
     * Extracts blue color intensities from pixels, present in given neighbourhood
     *
     * @param positionArrayList Pixel information from neighbourhood of a pixel
     * @return Set of blue color intensities from that neighbourhood
     */
    private int[] getBlueIntensities(ArrayList<Position> positionArrayList) {
        int[] intensities = new int[positionArrayList.size()];
        for (int i = 0; i < intensities.length; i++) {
            intensities[i] = positionArrayList.get(i).getIntensityB();
        }
        return intensities;
    }

    /**
     * Obtains neighbourhood of order X, around given pixel ( inclusive of itself )
     *
     * @param position Pixel information holder
     * @return Set of pixels around given pixel ( neighbourhood of order X )
     */
    private ArrayList<Position> getNeighbourHood(Position position) {
        return this.src.getNeighbourhoodOfOrderXInclusive(position, this.order);
    }

    /**
     * Set of pixels in specified row of image matrix
     *
     * @return Pixels present in given row
     */
    private Position[] extractRow() {
        return this.src.getPositions()[this.row];
    }

    /**
     * Sets color at specified pixel location
     *
     * @param position Pixel information holder
     * @param color    Color information
     */
    private void setIntensity(Position position, Color color) {
        this.sink.setRGB(position.getX(), position.getY(), color.getRGB());
    }

    @Override
    public void run() {
        for (Position position : this.extractRow()) {
            ArrayList<Position> positionArrayList = this.getNeighbourHood(position);
            this.setIntensity(position,
                    new Color(
                            this.mean(this.getRedIntensities(positionArrayList)),
                            this.mean(this.getGreenIntensities(positionArrayList)),
                            this.mean(this.getBlueIntensities(positionArrayList))
                    )
            );
        }
    }
}
