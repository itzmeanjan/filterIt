package in.itzmeanjan.filterit.segmentation;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
            if (hashMap.containsKey(i)) {
                probabilities[i] = ((double) hashMap.get(i)) / pixelC;
            } else {
                probabilities[i] = 0.0;
            }
        }
        return probabilities;
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
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < interClassVariance.length; i++) {
            executorService.execute(
                    new AutomaticThresholdingWorker(probabilities, i, interClassVariance)
            );
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            return -1;
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
