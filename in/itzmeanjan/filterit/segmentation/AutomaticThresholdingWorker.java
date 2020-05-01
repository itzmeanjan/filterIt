package in.itzmeanjan.filterit.segmentation;

/**
 * Worker class for concurrently finding inter class variance value for given threshold,
 * where intensity values < threshold belongs to first class & >= threshold
 * belongs to second class
 */
class AutomaticThresholdingWorker implements Runnable {

    private double[] probabilities;
    private int threshold;
    private double[] interClassVarianceArr;

    /**
     * Computes inter class variance for specific threshold value using following formula
     * <p>
     * classOneProbability * classTwoProbability * ( classOneMean - classTwoMean ) ^ 2
     * <p>
     * This operation is being performed concurrently for multiple threshold values
     *
     * @param probabilities         Probability of each pixel intensity level ∈ [0, 255]
     * @param threshold             Selected threshold for this run
     * @param interClassVarianceArr Shared array for storing inter class variance values
     */
    AutomaticThresholdingWorker(double[] probabilities, int threshold, double[] interClassVarianceArr) {
        this.probabilities = probabilities;
        this.threshold = threshold;
        this.interClassVarianceArr = interClassVarianceArr;
    }

    /**
     * Finds sum of probabilities for all intensity levels where intensity < threshold
     *
     * @return Sum of probabilities of all intensity levels < threshold
     */
    private double probabilityOfClassOne() {
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
     * @param classOneProb Sum of Probabilities of all pixel intensities < threshold
     * @return Mean of class one pixels
     */
    private double meanOfClassOne(double classOneProb) {
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
     * @param classTwoProb Sum of Probabilities of all pixel intensities >= threshold
     * @return Mean of class two pixels
     */
    private double meanOfClassTwo(double classTwoProb) {
        double classTwoMean = 0.0;
        for (int i = threshold; i < 256; classTwoMean += i * probabilities[i++]) ;
        return classTwoMean / classTwoProb;
    }

    /**
     * Computes inter class variance, using following formula
     * <p>
     * classOneProbability * classTwoProbability * ( classOneMean - classTwoMean ) ^ 2
     *
     * @return Inter class variance
     */
    private double interClassVariance() {
        double classOneProb = this.probabilityOfClassOne();
        double classTwoProb = this.probabilityOfClassTwo(classOneProb);
        double classOneMean = this.meanOfClassOne(classOneProb),
                classTwoMean = this.meanOfClassTwo(classTwoProb);
        return classOneProb * classTwoProb * Math.pow(classOneMean - classTwoMean, 2.0);
    }

    @Override
    public void run() {
        this.interClassVarianceArr[this.threshold] = this.interClassVariance();
    }
}
