package in.itzmeanjan.filterit.segmentation;

/**
 * Holds location, intensity & state information of a pixel in image
 */
public class Position {
    private final int x, y, intensityR, intensityG, intensityB;
    private int state;

    /**
     * Holds pixel information i.e. its location, intensity & state
     * <p>
     * Initially state will be set as inactive ( 0 )
     * <p>
     * When Pixel location has been explored, it can be set as active ( 1 )
     * <p>
     * And after all neighbours explored for this location, its state to be set as dead ( 2 )
     *
     * @param x          X-coordinate of Pixel location
     * @param y          Y-coordinate of Pixel location
     * @param intensityR Red color component intensity at I[y, x]
     * @param intensityG Green color component intensity at I[y, x]
     * @param intensityB Blue color component intensity at I[y, x]
     */
    public Position(int x, int y, int intensityR, int intensityG, int intensityB) {
        this.x = x;
        this.y = y;
        this.intensityR = intensityR;
        this.intensityG = intensityG;
        this.intensityB = intensityB;
        this.state = 0;
    }

    /**
     * @return X-coordinate of Pixel
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y-coordinate of Pixel
     */
    public int getY() {
        return y;
    }

    /**
     * @return Red color intensity at this location
     */
    public int getIntensityR() {
        return intensityR;
    }

    /**
     * @return Green color intensity at this location
     */
    public int getIntensityG() {
        return intensityG;
    }

    /**
     * @return Blue color intensity at this location
     */
    public int getIntensityB() {
        return intensityB;
    }

    /**
     * @return State of pixel ( inactive = 0, active = 1, dead = 2 )
     */
    public int getState() {
        return state;
    }

    /**
     * Updates state of Pixel
     *
     * @param state State of pixel
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Checks whether this pixel's red component intensity satisfies
     * <p>
     * targetIntensity - relaxation <= intensity <= targetIntensity + relaxation
     * <p>
     * or not
     *
     * @param targetIntensity target intensity, with which this pixel's intensity to be compared
     * @param relaxation      targetIntensity - relaxation <= intensity <= targetIntensity + relaxation, needs to be satisfied
     * @return Result of comparison as true / false
     */
    public boolean isIntensityRWithInRange(int targetIntensity, int relaxation) {
        return this.intensityR >= targetIntensity - relaxation && this.intensityR <= targetIntensity + relaxation;
    }

    /**
     * Checks whether this pixel's green component intensity satisfies
     * <p>
     * targetIntensity - relaxation <= intensity <= targetIntensity + relaxation
     * <p>
     * or not
     *
     * @param targetIntensity target intensity, with which this pixel's intensity to be compared
     * @param relaxation      targetIntensity - relaxation <= intensity <= targetIntensity + relaxation, needs to be satisfied
     * @return Result of comparison as true / false
     */
    public boolean isIntensityGWithInRange(int targetIntensity, int relaxation) {
        return this.intensityG >= targetIntensity - relaxation && this.intensityG <= targetIntensity + relaxation;
    }

    /**
     * Checks whether this pixel's blue component intensity satisfies
     * <p>
     * targetIntensity - relaxation <= intensity <= targetIntensity + relaxation
     * <p>
     * or not
     *
     * @param targetIntensity target intensity, with which this pixel's intensity to be compared
     * @param relaxation      targetIntensity - relaxation <= intensity <= targetIntensity + relaxation, needs to be satisfied
     * @return Result of comparison as true / false
     */
    public boolean isIntensityBWithInRange(int targetIntensity, int relaxation) {
        return this.intensityB >= targetIntensity - relaxation && this.intensityB <= targetIntensity + relaxation;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", intensityR=" + intensityR +
                ", intensityG=" + intensityG +
                ", intensityB=" + intensityB +
                ", state=" + state +
                '}';
    }
}
