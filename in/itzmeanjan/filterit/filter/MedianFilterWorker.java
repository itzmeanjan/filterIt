package in.itzmeanjan.filterit.filter;

import in.itzmeanjan.filterit.Pixel;
import in.itzmeanjan.filterit.segmentation.Image;
import in.itzmeanjan.filterit.segmentation.Position;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Actual worker class to be running on different threads ( may be at a time for multiprocessor
 * systems ) while applying Median filter of order ( >=1 ) on a given buffered image. Processes a
 * single pixel in an image.
 */
class MedianFilterWorker implements Runnable {

    private int order, row;
    private Image src;
    private BufferedImage sink;

    MedianFilterWorker(int order, int row, Image src, BufferedImage sink) {
        this.order = order;
        this.row = row;
        this.src = src;
        this.sink = sink;
    }

    /**
     * Swaps two elements of an array, indicated by their indices
     */
    private void swap(int posI, int posJ, int[] pixels) {
        int tmp = pixels[posI];
        pixels[posI] = pixels[posJ];
        pixels[posJ] = tmp;
    }

    /**
     * Sorts a set of integers using bubble sort cause we'll sorting pretty small number of elements,
     * so I'm staying with this O(n^2) algorithm
     */
    private void sort(int[] pxlVal) {
        for (int i = 0; i < pxlVal.length; i++) {
            for (int j = 0; j < pxlVal.length - i - 1; j++) {
                if (pxlVal[j] > pxlVal[j + 1]) {
                    swap(j, j + 1, pxlVal);
                }
            }
        }
    }


    /**
     * Computes median value of given pixel intensities
     *
     * @param pxlVal Pixel intensities
     * @return Median value of intensities
     */
    private int median(int[] pxlVal) {
        this.sort(pxlVal);
        if (pxlVal.length % 2 == 0) {
            return Math.round((float) (pxlVal[pxlVal.length / 2] + pxlVal[pxlVal.length / 2 - 1]) / (float) 2);
        }
        return pxlVal[pxlVal.length / 2];
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
     * @param position Pixel information holder ( location, intensity etc. )
     */
    private void setIntensity(Position position) {
        ArrayList<Position> positionArrayList = this.getNeighbourHood(position);
        this.sink.setRGB(position.getX(), position.getY(), new Color(
                this.median(this.getRedIntensities(positionArrayList)),
                this.median(this.getGreenIntensities(positionArrayList)),
                this.median(this.getBlueIntensities(positionArrayList))
        ).getRGB());
    }

    @Override
    public void run() {
        for (Position position : this.extractRow()) {
            this.setIntensity(position);
        }
    }
}
