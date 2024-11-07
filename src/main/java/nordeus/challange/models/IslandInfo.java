// IslandInfo.java
package nordeus.challange.models;

public class IslandInfo {
    private int sumHeights;
    private int cellCount;
    private int sumX; // Sum of row indices
    private int sumY; // Sum of column indices

    public IslandInfo() {
        this.sumHeights = 0;
        this.cellCount = 0;
        this.sumX = 0;
        this.sumY = 0;
    }

    public void addSumHeights(int height) {
        this.sumHeights += height;
    }

    public void incrementCellCount() {
        this.cellCount++;
    }

    public void addCoordinates(int x, int y) {
        this.sumX += x;
        this.sumY += y;
    }

    public int getSumHeights() {
        return sumHeights;
    }

    public int getCellCount() {
        return cellCount;
    }

    public int getSumX() {
        return sumX;
    }

    public int getSumY() {
        return sumY;
    }
}
