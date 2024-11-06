package nordeus.cnallange.businessLogic;

import nordeus.cnallange.models.CommandResponse;
import nordeus.cnallange.models.IslandData;
import nordeus.cnallange.models.IslandInfo;
import nordeus.cnallange.service.GameServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameBL implements GameBLI {

    private final GameServiceI _gameService;

    @Autowired
    public GameBL(GameServiceI gameService) {
        this._gameService = gameService;
    }

    @Override
    public CommandResponse<IslandData> getCalculatedGameData() {
        // Step 1: Retrieve the map data from _gameService
        CommandResponse<int[][]> response = _gameService.getIslandData();
        if (!response.isSuccess()) {
            return new CommandResponse<>(false, response.getMessage(), null);
        }

        // Step 2: Store the map data in a local variable
        int[][] mapData = response.getData();
        if (mapData == null) {
            String errorMessage = "Map data is not available.";
            return new CommandResponse<>(false, errorMessage, null);
        }

        // Proceed to calculate island data
        IslandData islandData = calculateIslandData(mapData);

        // Optionally, print the average height map and per-island averages
        printAverageHeightMap(islandData.getAvgHeightMap());
        printIslandAverageHeights(islandData.getIslandAvgHeights());

        // Step 7: Return the island data in a CommandResponse
        return new CommandResponse<>(true, "", islandData);
    }

    // New method to calculate island data, keeping all variables local
    private IslandData calculateIslandData(int[][] mapData) {
        int rows = mapData.length;
        int cols = mapData[0].length;

        // Use islandIdsMatrix array to keep track of island IDs and visited cells
        int[][] islandIdsMatrix = new int[rows][cols]; // 0 = unvisited or water, >0 = island ID
        int islandId = 1; // Start island IDs from 1

        // Map to store islandId -> sum of heights and cell count
        Map<Integer, IslandInfo> islandInfoMap = new HashMap<>();

        // Perform DFS on each unvisited land cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mapData[i][j] > 0 && islandIdsMatrix[i][j] == 0) {
                    IslandInfo info = new IslandInfo();
                    dfs(i, j, mapData, islandIdsMatrix, islandId, info);
                    islandInfoMap.put(islandId, info);
                    islandId++;
                }
            }
        }

        // Compute average heights for each island
        Map<Integer, Double> islandAvgHeights = new HashMap<>();
        double maxAverageHeight = Double.NEGATIVE_INFINITY;
        int islandWithMaxAvgHeightId = -1;

        for (var entry : islandInfoMap.entrySet()) {
            int id = entry.getKey();
            IslandInfo info = entry.getValue();
            double avgHeight = (double) info.getSumHeights() / info.getCellCount();
            islandAvgHeights.put(id, avgHeight);

            // Check if this island has the maximum average height
            if (avgHeight > maxAverageHeight) {
                maxAverageHeight = avgHeight;
                islandWithMaxAvgHeightId = id;
            }
        }

        // Create a matrix where each cell of an island is filled with the average height
        double[][] avgHeightMap = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mapData[i][j] > 0) {
                    int id = islandIdsMatrix[i][j];
                    avgHeightMap[i][j] = islandAvgHeights.get(id);
                } else {
                    avgHeightMap[i][j] = 0;
                }
            }
        }

        // Return the processed island data
        return new IslandData(islandIdsMatrix, islandAvgHeights, islandWithMaxAvgHeightId, maxAverageHeight, avgHeightMap);
    }

    // DFS method to traverse the island
    private void dfs(int i, int j, int[][] mapData, int[][] islandIds, int islandId, IslandInfo info) {
        int rows = mapData.length;
        int cols = mapData[0].length;

        // Boundary and base conditions
        if (i < 0 || i >= rows || j < 0 || j >= cols) return;
        if (islandIds[i][j] != 0 || mapData[i][j] == 0) return;

        islandIds[i][j] = islandId;
        info.addSumHeights(mapData[i][j]);
        info.incrementCellCount();

        // Visit all four adjacent cells
        dfs(i - 1, j, mapData, islandIds, islandId, info); // Up
        dfs(i + 1, j, mapData, islandIds, islandId, info); // Down
        dfs(i, j - 1, mapData, islandIds, islandId, info); // Left
        dfs(i, j + 1, mapData, islandIds, islandId, info); // Right
    }

    // Helper method to print the average height map
    private void printAverageHeightMap(double[][] avgHeightMap) {
        System.out.println("Average Height Map:");
        for (double[] row : avgHeightMap) {
            for (double val : row) {
                System.out.printf("%.2f ", val);
            }
            System.out.println();
        }
    }

    // Helper method to print the average heights per island
    private void printIslandAverageHeights(Map<Integer, Double> islandAvgHeights) {
        System.out.println("Island Average Heights:");
        for (Map.Entry<Integer, Double> entry : islandAvgHeights.entrySet()) {
            System.out.printf("Island %d: Average Height = %.2f\n", entry.getKey(), entry.getValue());
        }
    }

    // Method to determine if the island at (x, y) has the highest average height
    public boolean isIslandWithHighestAverageHeight(int x, int y) {
        // Since we don't have instance variables anymore, we need to retrieve and process the data again
        CommandResponse<IslandData> response = getCalculatedGameData();
        if (!response.isSuccess()) {
            System.err.println("Error calculating island data: " + response.getMessage());
            return false;
        }

        IslandData islandData = response.getData();
        int[][] islandIds = islandData.getIslandIds();
        int[][] mapData = islandData.getMapData(); // We need to store mapData in IslandData now

        int rows = mapData.length;
        int cols = mapData[0].length;

        // Validate coordinates
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            System.err.println("Coordinates out of bounds.");
            return false;
        }

        int islandIdAtCoordinates = islandIds[x][y];

        if (islandIdAtCoordinates == 0) {
            System.out.println("The selected coordinates are water.");
            return false;
        }

        int maxAvgHeightIslandId = islandData.getIslandWithMaxAvgHeightId();

        if (islandIdAtCoordinates == maxAvgHeightIslandId) {
            System.out.println("The selected island has the highest average height.");
            return true;
        } else {
            System.out.println("The selected island does not have the highest average height.");
            return false;
        }
    }
}
