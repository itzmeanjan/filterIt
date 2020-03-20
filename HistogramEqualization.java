import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

class HistogramEqualization {

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

    private double probabilityofIntensity(int intensity, int pixelC, HashMap<Integer, Integer> hMap) {
        return hMap.containsKey(intensity) ? (double) hMap.get(intensity) / (double) pixelC : 0;
    }

    private double[] computeProbabilities(int pixelC, HashMap<Integer, Integer> hMap) {
        double[] prob = new double[256];
        for (int i = 0; i < 256; prob[i] = this.probabilityofIntensity(i++, pixelC, hMap))
            ;
        return prob;
    }

    private double getCDF(int intensity, double[] prob) {
        double cdf = 0.0;
        for (int i = 0; i <= intensity; cdf += prob[i++])
            ;
        return cdf;
    }

    private double[] computeCDFs(double[] prob) {
        double[] cdfs = new double[256];
        for (int i = 0; i < 256; cdfs[i] = this.getCDF(i++, prob))
            ;
        return cdfs;
    }

    private double getMinCDF(double[] cdfs) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < cdfs.length; i++) {
            if (min > cdfs[i]) {
                min = cdfs[i];
            }
        }
        return min;
    }

    private int transformPixel(double minCDF, double cdf, int maxIntensity) {
        return (int) Math.round(((cdf - minCDF) / (1.0 - minCDF)) * maxIntensity);
    }

    BufferedImage transform(BufferedImage img) {
        if (img == null)
            return null;
        img = new GrayScale().grayscale(img);
        double[] cdfs = this.computeCDFs(this.computeProbabilities(img.getWidth() * img.getHeight(),
                this.getFrequencyDistributionOfIntensities(img)));
        double minCDF = this.getMinCDF(cdfs);
        BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < transformed.getHeight(); i++) {
            for (int j = 0; j < transformed.getWidth(); j++) {
                int transformedIntensity = this.transformPixel(minCDF, cdfs[new Color(img.getRGB(j, i)).getRed()], 255);
                transformed.setRGB(j, i,
                        (new Color(transformedIntensity, transformedIntensity, transformedIntensity)).getRGB());
            }
        }
        return transformed;
    }

    BufferedImage transform(String src) {
        return this.transform(ImportExportImage.importImage(src));
    }

    public static void main(String[] args) {
        HistogramEqualization hEqualization = new HistogramEqualization();
        System.out.println(ImportExportImage.exportImage(hEqualization.transform("./examples/circulatory_sys.jpg"),
                "./examples/histogramEqualized.jpg"));
    }

}