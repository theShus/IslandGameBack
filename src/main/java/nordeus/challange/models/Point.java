// Point.java
package nordeus.challange.models;

public class Point {
    private final int x; // Row index
    private final int y; // Column index

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
