package in.itzmeanjan.filterit.segmentation;

class Position {
    private int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position[][] getNeighbours() {
        Position[][] neighbours = new Position[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                neighbours[i][j] = new Position(this.x + i - 1, this.y + j - 1);
            }
        }
        return neighbours;
    }
}
