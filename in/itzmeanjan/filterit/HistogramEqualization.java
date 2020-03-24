package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Applies histrogram equalization transformation to distribute pixel intensity
 * values all over whole range of possible pixel values ( i.e. 0 - 255 ), for a
 * given image. But this method can be only applied on grayscaled images, so
 * we'll first grayscale it & then perform further ops on top of it
 */
public class HistogramEqualization {

    /**
     * Given a grayscale image we'll obtain a mapping from pixel intensity ->
     * occurance count
     */
    private HashMap<Integer, Integer> getFrequencyDistributionOfIntensities(BufferedImage img) {
        HashMap<Integer, Integer> hMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int intensity = (new Color(img.getRGB(j, i))).getRed();
                if (!hMap.containsKey(intensity)) {
                    hMap.put(intensity, 1);
                } else {
                    int freq = hMap.get(intensity);
                    hMap.put(intensity, freq + 1);
                }
            }
        }
        return hMap;
    }

    /**
     * Given one certain intensity value ( >=0 && <=255 ), we'll find probability of
     * its occurance using this formula : #-of occurances of intensity / #-of pixels
     */
    private double probabilityofIntensity(int intensity, int pixelC, HashMap<Integer, Integer> hMap) {
        return hMap.containsKey(intensity) ? (double) hMap.get(intensity) / (double) pixelC : 0;
    }

    /**
     * Computes & caches all pixel intensity probability values
     */
    private double[] computeProbabilities(int pixelC, HashMap<Integer, Integer> hMap) {
        double[] prob = new double[256];
        for (int i = 0; i < 256; prob[i] = this.probabilityofIntensity(i++, pixelC, hMap))
            ;
        return prob;
    }

    /**
     * Computes Cumulative Distribution Function for a given intensity value, using
     * this formula : Σ p(i), i = 0; i <= intensity; i++, were p(i) = probability of
     * finding intensity value i ( 0 <= i <= 255 ), which is precomputed & cached
     */
    private double getCDF(int intensity, double[] prob) {
        double cdf = 0.0;
        for (int i = 0; i <= intensity; cdf += prob[i++])
            ;
        return cdf;
    }

    /**
     * Computes all CDF values for each of possible pixel intensities ( 0 - 255 ) &
     * caches them for further requirement
     */
    private double[] computeCDFs(double[] prob) {
        double[] cdfs = new double[256];
        for (int i = 0; i < 256; cdfs[i] = this.getCDF(i++, prob))
            ;
        return cdfs;
    }

    /**
     * Finds minimum CDF value from all possible CDF values ( i.e. all possible 256
     * CDFs )
     */
    private double getMinCDF(double[] cdfs) {
        double min = Double.MAX_VALUE;
        for (double v : cdfs) {
            if (min > v) {
                min = v;
            }
        }
        return min;
    }

    /**
     * Given a image ( which is already read in a BufferedImage object ), we'll
     * first grayscale it ( grayscaling one already grayscaled image doesn't cause
     * any side effects ), & then for each pixel intensity value we'll obtain
     * transformed pixel intensity value i.e. after applying histrogram equalization
     * 
     * Finally updated image buffer to be returned which can be either exported into
     * file & can be used for further processing purposes.
     * 
     * Concurrency support incorporated using thread pool.
     */
    BufferedImage transform(BufferedImage img) {
        if (img == null)
            return null;
        img = new GrayScale().grayscale(img);
        double[] cdfs = this.computeCDFs(this.computeProbabilities(img.getWidth() * img.getHeight(),
                this.getFrequencyDistributionOfIntensities(img)));
        double minCDF = this.getMinCDF(cdfs);
        ExecutorService eService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                eService.execute(
                        new HistogramEqualizationWorker(i, j, minCDF, cdfs, new Color(img.getRGB(j, i)), transformed));
            }
        }
        eService.shutdown();
        return transformed;
    }

    /**
     * If you want to just pass a path to an image, it'll read it & then return
     * histogram equalized image
     */
    BufferedImage transform(String src) {
        return this.transform(ImportExportImage.importImage(src));
    }

}