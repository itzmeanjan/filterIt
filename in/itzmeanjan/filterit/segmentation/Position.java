package in.itzmeanjan.filterit.segmentation;

class Position {
    private final int x, y, intensity;
    private int state;

    Position(int x, int y, int intensity) {
        this.x = x;
        this.y = y;
        this.intensity = intensity;
        this.state = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIntensity() {
        return intensity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
