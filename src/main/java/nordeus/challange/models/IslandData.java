package nordeus.challange.models;

import java.util.Map;

public class IslandData {
    private final int[][] islandIds;
    private final Map<Integer, Integer> islandAvgHeights;
    private final int islandWithMaxAvgHeightId;
    private final int[][] mapData;
    private final Map<Integer, Point> islandCenterPoints;

    public IslandData(int[][] islandIds, Map<Integer, Integer> islandAvgHeights, int islandWithMaxAvgHeightId, int[][] mapData, Map<Integer,
            Point> islandCenterPoints) {
        this.islandIds = islandIds;
        this.islandAvgHeights = islandAvgHeights;
        this.islandWithMaxAvgHeightId = islandWithMaxAvgHeightId;
        this.mapData = mapData;
        this.islandCenterPoints = islandCenterPoints;
    }

    public int[][] getIslandIds() {
        return islandIds;
    }

    public Map<Integer, Integer> getIslandAvgHeights() {
        return islandAvgHeights;
    }

    public int getIslandWithMaxAvgHeightId() {
        return islandWithMaxAvgHeightId;
    }

    public int[][] getMapData() {
        return mapData;
    }

    public Map<Integer, Point> getIslandCenterPoints() {
        return islandCenterPoints;
    }
}
