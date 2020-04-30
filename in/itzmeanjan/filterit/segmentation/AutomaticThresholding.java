package in.itzmeanjan.filterit.segmentation;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Given a buffered image with bimodal histogram, we'll segment that image
 * into foreground and background using automatic thresholding mechanism.
 * <p>
 * This one implements Otsu's Algorithm.
 */
public class AutomaticThresholding {

    /**
     * Finds frequency distribution of each of intensity
     * level ∈ [0, 255] for given 8-bit grayscale image
     *
     * @param img Buffered image to be segmented
     * @return Frequency distribution of each of possible pixel intensity level
     */
    private HashMap<Integer, Integer> getFrequencyDistribution(BufferedImage img) {
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                if (hashMap.containsKey(color.getRed())) {
                    hashMap.put(color.getRed(), hashMap.get(color.getRed()) + 1);
                } else {
                    hashMap.put(color.getRed(), 1);
                }
            }
        }
        return hashMap;
    }

    /**
     * Computes probability of each pixel intensity level ( 256 levels for 8-bit image )
     *
     * @param hashMap Frequency distribution of pixel intensity values
     * @param pixelC  Total pixel count in image
     * @return Probabilities of pixel intensity levels, where P[i] denotes probability of Pixel intensity value `i`, kept in `i` index of array.
     */
    private double[] getProbabilities(HashMap<Integer, Integer> hashMap, int pixelC) {
        double[] probabilities = new double[256];
        for (int i = 0; i < 256; i++) {
            probabilities[i] = ((double) hashMap.get(i)) / pixelC;
        }
        return probabilities;
    }

    /**
     * Finds sum of probabilities for all intensity levels where intensity < threshold
     *
     * @param probabilities Probabilities of pixel intensity values
     * @param threshold     Selected threshold ∈ [0, 255]
     * @return Sum of probabilities of all intensity levels < threshold
     */
    private double probabilityOfClassOne(double[] probabilities, int threshold) {
        double classOneProb = 0.0;
        for (int i = 0; i < threshold; classOneProb += probabilities[i++]) ;
        return classOneProb;
    }

    /**
     * >= threshold, class's probability, which is nothing but 1 - probabilityOfClassOne
     * <p>
     * probabilityOfClassOne + probabilityOfClassTwo = 1
     *
     * @param classOneProb Probability of all pixels belonging to < threshold, class
     * @return Sum of probabilities of all intensity levels >= threshold
     */
    private double probabilityOfClassTwo(double classOneProb) {
        return 1.0 - classOneProb;
    }

    /**
     * Calculates mean of pixel intensities for class One ( intensity values < threshold ),
     * using this formula
     * <p>
     * Σ P(i) / classOneProbability, for  i = 0 .. (threshold - 1)
     *
     * @param probabilities Probabilities of pixel intensity values
     * @param threshold     Selected threshold ∈ [0, 255]
     * @param classOneProb  Sum of Probabilities of all pixel intensities < threshold
     * @return Mean of class one pixels
     */
    private double meanOfClassOne(double[] probabilities, int threshold, double classOneProb) {
        double classOneMean = 0.0;
        for (int i = 0; i < threshold; classOneMean += i * probabilities[i++]) ;
        return classOneMean / classOneProb;
    }

    /**
     * Calculates mean of pixel intensities for class Two ( intensity values >= threshold ),
     * using this formula
     * <p>
     * Σ P(i) / classTwoProbability, for  i = threshold .. 255
     *
     * @param probabilities Probabilities of pixel intensity values
     * @param threshold     Selected threshold ∈ [0, 255]
     * @param classTwoProb  Sum of Probabilities of all pixel intensities >= threshold
     * @return Mean of class two pixels
     */
    private double meanOfClassTwo(double[] probabilities, int threshold, double classTwoProb) {
        double classTwoMean = 0.0;
        for (int i = threshold; i < 256; classTwoMean += i * probabilities[i++]) ;
        return classTwoMean / classTwoProb;
    }

    /**
     * Computes inter class variance, using following formula
     * <p>
     * classOneProbability * classTwoProbability * ( classOneMean - classTwoMean ) ^ 2
     *
     * @param probabilities Probabilities of pixel intensity levels
     * @param threshold     Selected threshold value ∈ [0, 255]
     * @return Inter class variance
     */
    private double interClassVariance(double[] probabilities, int threshold) {
        double classOneProb = this.probabilityOfClassOne(probabilities, threshold);
        double classTwoProb = this.probabilityOfClassTwo(classOneProb);
        double classOneMean = this.meanOfClassOne(probabilities, threshold, classOneProb),
                classTwoMean = this.meanOfClassTwo(probabilities, threshold, classTwoProb);
        return classOneProb * classTwoProb * Math.pow(classOneMean - classTwoMean, 2.0);
    }

    /**
     * Tries to find out suitable threshold value so that image can be segmented into
     * background and foreground
     * <p>
     * Works well with images having bimodal histogram
     *
     * @param probabilities Probabilities of pixel intensity levels
     * @return Threshold value found after automatic detection
     */
    private int computeThresholdValue(double[] probabilities) {
        double[] interClassVariance = new double[256];
        for (int i = 0; i < interClassVariance.length; i++) {
            interClassVariance[i] = this.interClassVariance(probabilities, i);
        }
        int thresholdIndex = -1;
        double thresholdV = Double.MIN_VALUE;
        for (int i = 0; i < interClassVariance.length; i++) {
            if (interClassVariance[i] > thresholdV) {
                thresholdV = interClassVariance[i];
                thresholdIndex = i;
            }
        }
        return thresholdIndex;
    }

    /**
     * Performs automatic thresholding based image segmentation op, using Otsu's Algorithm
     *
     * @param img Image to be segmented
     * @return Segmented image
     */
    public BufferedImage segment(BufferedImage img) {
        if (img == null) {
            return null;
        }
        img = new GrayScale().grayscale(img);
        BufferedImage sink = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int threshold = this.computeThresholdValue(
                this.getProbabilities(
                        this.getFrequencyDistribution(img),
                        img.getHeight() * img.getWidth()));
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                if (new Color(img.getRGB(j, i)).getRed() < threshold) {
                    sink.setRGB(j, i, new Color(0, 0, 0).getRGB());
                } else {
                    sink.setRGB(j, i, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return sink;
    }

    /**
     * Performs automatic thresholding based image segmentation op, using Otsu's Algorithm
     *
     * @param img Image to be segmented
     * @return Segmented image
     */
    public BufferedImage segment(String img) {
        return this.segment(ImportExportImage.importImage(img));
    }
}
