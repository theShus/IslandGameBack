package nordeus.challange.models;

import java.util.Map;

public class IslandData {
    private final int[][] islandIds; // Map of cell coordinates to island IDs
    private final Map<Integer, Double> islandAvgHeights; // Map of island IDs to average heights
    private final int islandWithMaxAvgHeightId; // Island ID with the maximum average height
    private final double maxAverageHeight; // The maximum average height among all islands
    private final double[][] avgHeightMap; // Matrix of average heights per cell
    private final int[][] mapData; // Original map data

    public IslandData(int[][] islandIds, Map<Integer, Double> islandAvgHeights, int islandWithMaxAvgHeightId, double maxAverageHeight, double[][] avgHeightMap) {
        this.islandIds = islandIds;
        this.islandAvgHeights = islandAvgHeights;
        this.islandWithMaxAvgHeightId = islandWithMaxAvgHeightId;
        this.maxAverageHeight = maxAverageHeight;
        this.avgHeightMap = avgHeightMap;
        this.mapData = null; // Set to null since it's not provided
    }

    public IslandData(int[][] islandIds, Map<Integer, Double> islandAvgHeights, int islandWithMaxAvgHeightId, double maxAverageHeight, double[][] avgHeightMap, int[][] mapData) {
        this.islandIds = islandIds;
        this.islandAvgHeights = islandAvgHeights;
        this.islandWithMaxAvgHeightId = islandWithMaxAvgHeightId;
        this.maxAverageHeight = maxAverageHeight;
        this.avgHeightMap = avgHeightMap;
        this.mapData = mapData;
    }

    public int[][] getIslandIds() {
        return islandIds;
    }

    public Map<Integer, Double> getIslandAvgHeights() {
        return islandAvgHeights;
    }

    public int getIslandWithMaxAvgHeightId() {
        return islandWithMaxAvgHeightId;
    }

    public double getMaxAverageHeight() {
        return maxAverageHeight;
    }

    public double[][] getAvgHeightMap() {
        return avgHeightMap;
    }

    public int[][] getMapData() {
        return mapData;
    }
}
