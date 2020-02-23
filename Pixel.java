import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Color;

class Pixel {
    int width, height, posX, posY;

    Pixel(int w, int h, int i, int j) {
        this.width = w;
        this.height = h;
        this.posX = i;
        this.posY = j;
    }

    Pixel copy() {
        return new Pixel(this.width, this.height, this.posX, this.posY);
    }

    private boolean isValid() {
        return (this.posX >= 0 && this.posX < this.height) && (this.posY >= 0 && this.posY < this.width);
    }

    private int getMaskSizeFromOrder(int order) {
        return (int) Math.pow(2.0, order) + 1;
    }

    private ArrayList<Pixel> getSpiral(Pixel cur, int width) {
        ArrayList<Pixel> spiral = new ArrayList<Pixel>();
        int target = cur.posX + width - 1;
        while (cur.posX < target) {
            Pixel tmp = cur.copy();
            cur.posX++;
            if (!tmp.isValid()) {
                continue;
            }
            spiral.add(tmp);
        }
        cur.posX--;
        cur.posY--;
        target = cur.posY - width + 1;
        while (cur.posY > target) {
            Pixel tmp = cur.copy();
            cur.posY--;
            if (!tmp.isValid()) {
                continue;
            }
            spiral.add(tmp);
        }
        cur.posY++;
        cur.posX--;
        target = cur.posX - width + 1;
        while (cur.posX > target) {
            Pixel tmp = cur.copy();
            cur.posX--;
            if (!tmp.isValid()) {
                continue;
            }
            spiral.add(tmp);
        }
        cur.posX++;
        cur.posY++;
        target = cur.posY + width - 1;
        while (cur.posY < target) {
            Pixel tmp = cur.copy();
            cur.posY++;
            if (!tmp.isValid()) {
                continue;
            }
            spiral.add(tmp);
        }
        return spiral;
    }

    private ArrayList<Pixel> getNeighbours(int order) {
        int maskSize = this.getMaskSizeFromOrder(order);
        ArrayList<Pixel> neighbours = new ArrayList<Pixel>();
        Pixel cur = this.copy();
        neighbours.add(cur.copy());
        cur.posY++;
        for (int i = 3; i <= maskSize; i += 2) {
            neighbours.addAll(this.getSpiral(cur, i));
        }
        return neighbours;
    }

    int[] getNeighbouringPixelsFromImage(BufferedImage img, char colorComponent, int order) {
        ArrayList<Pixel> neighbours = this.getNeighbours(order);
        int[] pixelV = new int[neighbours.size()];
        for (int i = 0; i < pixelV.length; i++) {
            switch (colorComponent) {
                case 'r':
                    pixelV[i] = (new Color(img.getRGB(neighbours.get(i).posY, neighbours.get(i).posX))).getRed();
                    break;
                case 'g':
                    pixelV[i] = (new Color(img.getRGB(neighbours.get(i).posY, neighbours.get(i).posX))).getGreen();
                    break;
                case 'b':
                    pixelV[i] = (new Color(img.getRGB(neighbours.get(i).posY, neighbours.get(i).posX))).getBlue();
                    break;
            }
        }
        return pixelV;
    }

    public String toString() {
        return "Pixel : " + this.posX + ", " + this.posY;
    }
}