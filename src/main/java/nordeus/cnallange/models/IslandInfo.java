package nordeus.cnallange.models;

public class IslandInfo {
    int sumHeights = 0;
    int cellCount = 0;

    public int getSumHeights() {
        return sumHeights;
    }

    public int getCellCount() {
        return cellCount;
    }

    public void addSumHeights(int value) {
        this.sumHeights += value;
    }

    public void incrementCellCount() {
        this.cellCount++;
    }

}


