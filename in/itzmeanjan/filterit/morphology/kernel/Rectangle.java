package in.itzmeanjan.filterit.morphology.kernel;


public class Rectangle {

    private int w, h;

    private Rectangle(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    public Point getCenter() {
        return new Point(this.w / 2, this.h / 2);
    }

    public Point[][] getAllPoints() {
        Point[][] points = new Point[this.h][this.w];
        int centX = this.w / 2, centY = this.h / 2;
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.w; j++) {
                points[i][j] = new Point(centX + j - 1, centY + i - 1);
            }
        }
        return points;
    }

    public static Rectangle getRect(int w, int h) {
        if (Rectangle.isWidthValid(w) && Rectangle.isHeightValid(h) && w != h) {
            return new Rectangle(w, h);
        }
        return null;
    }

    private static boolean isWidthValid(int w) {
        return w % 2 != 0;
    }

    private static boolean isHeightValid(int h) {
        return h % 2 != 0;
    }
}
