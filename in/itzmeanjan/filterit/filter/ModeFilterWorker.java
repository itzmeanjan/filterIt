package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.segmentation.Image;
import in.itzmeanjan.filterit.segmentation.Position;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Worker to be thrown to threads available at thread pool at a given time, for computing max pixel
 * intensity value around each pixel location present in specific row; neighbourhood is defined by
 * order of filtering ( that's supplied & > 0 , making it generalized )
 */
class ModeFilterWorker implements Runnable {

    private int order, row;
    private Image src;
    private BufferedImage sink;

    ModeFilterWorker(int order, int row, Image src, BufferedImage sink) {
        this.order = order;
        this.row = row;
        this.src = src; // image on which filter to be applied
        this.sink = sink; // filtered image, to be returned by ModeFilter.filter
    }

    /**
     * Computes max amplitude pixel intensity value from
     * neighborhood of a certain pixel ( exclusive of itself )
     */
    private int mode(int[] pxlVal) {
        int max = Integer.MIN_VALUE;
        for (int v : pxlVal) {
            if (v > max) {
                max = v;
            }
        }
        return max;
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
            this.setIntensity(position, new Color(this.mode(this.getRedIntensities(positionArrayList)),
                    this.mode(this.getGreenIntensities(positionArrayList)),
                    this.mode(this.getBlueIntensities(positionArrayList))));
        }
    }
}
