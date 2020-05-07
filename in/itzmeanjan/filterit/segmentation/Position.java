package in.itzmeanjan.filterit.segmentation;

/**
 * Holds location, intensity & state information of a pixel in image
 */
class Position {
    private final int x, y, intensity;
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
     * @param x         X-coordinate of Pixel location
     * @param y         Y-coordinate of Pixel location
     * @param intensity Color intensity at I[y, x]
     */
    Position(int x, int y, int intensity) {
        this.x = x;
        this.y = y;
        this.intensity = intensity;
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
     * @return Intensity at this location
     */
    public int getIntensity() {
        return intensity;
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
     * Checks whether this pixel's intensity satisfies
     * <p>
     * targetIntensity - relaxation <= intensity <= targetIntensity + relaxation
     * <p>
     * or not
     *
     * @param targetIntensity target intensity, with which this pixel's intensity to be compared
     * @param relaxation      targetIntensity - relaxation <= intensity <= targetIntensity + relaxation, needs to be satisfied
     * @return
     */
    public boolean isIntensityWithInRange(int targetIntensity, int relaxation) {
        return this.intensity >= targetIntensity - relaxation && this.intensity <= targetIntensity + relaxation;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", intensity=" + intensity +
                ", state=" + state +
                '}';
    }
}
