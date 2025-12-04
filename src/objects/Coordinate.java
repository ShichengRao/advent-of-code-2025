package src.objects;

import java.util.ArrayList;
import java.util.List;

public class Coordinate {
    public int x;
    public int y;
    public int z;

    public int weight = 0;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate that = (Coordinate) obj;
        return x == that.x && y == that.y && z == that.z;
    }

    public List<Coordinate> allNeighbors() {
        List<Coordinate> neighbors = new ArrayList<>();
        for (int xdelta = -1; xdelta <= 1; xdelta++) {
            for (int ydelta = -1; ydelta <= 1; ydelta++) {
                if (xdelta != 0 || ydelta != 0) {
                    neighbors.add(new Coordinate(x + xdelta, y + ydelta));
                }
            }
        }
        return neighbors;
    }

    public List<Coordinate> diagonalNeighbors() {
        List<Coordinate> neighbors = new ArrayList<>();
        for (int xdelta = -1; xdelta <= 1; xdelta++) {
            for (int ydelta = -1; ydelta <= 1; ydelta++) {
                if (xdelta != 0 && ydelta != 0) {
                    neighbors.add(new Coordinate(x + xdelta, y + ydelta));
                }
            }
        }
        return neighbors;
    }

    @Override
    public int hashCode() {
        return 10000 * x + 100 * y + z;
    }

    public String toString() {
        return (x + " " + y);
    }

    public String augmentedToString() {
        return (x + " " + y + " " + z + " " + weight);
    }
}
