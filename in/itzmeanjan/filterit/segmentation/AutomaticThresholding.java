package in.itzmeanjan.filterit.segmentation;

import in.itzmeanjan.filterit.GrayScale;
import in.itzmeanjan.filterit.ImportExportImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class AutomaticThresholding {

    private HashMap<Integer, Integer> getFrequencyDistribution(BufferedImage img) {
        if (img == null) {
            return null;
        }
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

    private double[] getProbabilities(HashMap<Integer, Integer> hashMap, int pixelC) {
        double[] probabilities = new double[256];
        for (int i = 0; i < 256; i++) {
            probabilities[i] = ((double) hashMap.get(i)) / pixelC;
        }
        return probabilities;
    }

    private double probabilityOfClassOne(double[] probabilities, int threshold) {
        double classOneProb = 0.0;
        for (int i = 0; i < threshold; classOneProb += probabilities[i++]) ;
        return classOneProb;
    }

    private double probabilityOfClassTwo(double classOneProb) {
        return 1.0 - classOneProb;
    }

    private double meanOfClassOne(double[] probabilities, int threshold, double classOneProb) {
        double classOneMean = 0.0;
        for (int i = 0; i < threshold; classOneMean += i * probabilities[i++]) ;
        return classOneMean / classOneProb;
    }

    private double meanOfClassTwo(double[] probabilities, int threshold, double classTwoProb) {
        double classTwoMean = 0.0;
        for (int i = threshold; i < 256; classTwoMean += i * probabilities[i++]) ;
        return classTwoMean / classTwoProb;
    }

    private double interClassVariance(double[] probabilities, int threshold) {
        double classOneProb = this.probabilityOfClassOne(probabilities, threshold);
        double classTwoProb = this.probabilityOfClassTwo(classOneProb);
        double classOneMean = this.meanOfClassOne(probabilities, threshold, classOneProb),
                classTwoMean = this.meanOfClassTwo(probabilities, threshold, classTwoProb);
        return classOneProb * classTwoProb * Math.pow(classOneMean - classTwoMean, 2.0);
    }

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

    public BufferedImage segment(String img) {
        return this.segment(ImportExportImage.importImage(img));
    }
}
