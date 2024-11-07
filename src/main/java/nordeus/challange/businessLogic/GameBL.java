package nordeus.challange.businessLogic;

import nordeus.challange.models.*;
import nordeus.challange.service.GameServiceI;
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
    public CommandResponse<IslandData> getCalculatedGameDataSmooth() {
//        CommandResponse<IslandData> islandDataRough = getCalculatedGameDataRough();
//        if (!islandDataRough.isSuccess()) {
//            return new CommandResponse<>(false, islandDataRough.getMessage(), null);
//        }


        return null;
    }

    public double[][] generateHeightMap(int[][] mapData, int[][] islandIds, long seed) {
        int width = mapData.length;
        int height = mapData[0].length;
        double[][] heightMap = new double[width][height];

        PerlinNoise perlinNoise = new PerlinNoise(seed);

        // Noise parameters
        double scale = 0.1; // Adjust the scale to change the "zoom" of the noise
        int octaves = 4;    // Number of layers of noise
        double persistence = 0.5; // Controls the amplitude of each octave
        double lacunarity = 2.0;  // Controls the frequency of each octave

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (islandIds[x][y] != 0) {
                    // Generate Perlin noise value for this coordinate
                    double noiseValue = 0;
                    double frequency = 1;
                    double amplitude = 1;
                    double maxAmplitude = 0;

                    for (int i = 0; i < octaves; i++) {
                        double sampleX = x * scale * frequency;
                        double sampleY = y * scale * frequency;

                        double perlinValue = perlinNoise.noise(sampleX, sampleY);
                        noiseValue += perlinValue * amplitude;

                        maxAmplitude += amplitude;
                        amplitude *= persistence;
                        frequency *= lacunarity;
                    }

                    // Normalize the result
                    noiseValue /= maxAmplitude;

                    // Apply any desired transformation, e.g., scaling heights
                    heightMap[x][y] = noiseValue;
                } else {
                    // Sea level
                    heightMap[x][y] = 0;
                }
            }
        }

        return heightMap;
    }


    @Override
    public CommandResponse<IslandData> test() {
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

        return new CommandResponse<>(true, "", islandData);
    }

    // Updated method to calculate island data with integer rounding
    private IslandData calculateIslandData(int[][] mapData) {
        int rows = mapData.length;
        int cols = mapData[0].length;

        int[][] islandIdsMatrix = new int[rows][cols];
        int islandId = 1;

        Map<Integer, IslandInfo> islandInfoMap = new HashMap<>();

        // Perform DFS to find islands and accumulate data
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

        // Compute average heights and center points
        Map<Integer, Integer> islandAvgHeights = new HashMap<>();
        Map<Integer, Point> islandCenterPoints = new HashMap<>();
        int maxAverageHeight = Integer.MIN_VALUE;
        int islandWithMaxAvgHeightId = -1;

        for (Map.Entry<Integer, IslandInfo> entry : islandInfoMap.entrySet()) {
            int id = entry.getKey();
            IslandInfo info = entry.getValue();

            // Calculate average height and round to nearest integer
            int avgHeight = (int) Math.round((double) info.getSumHeights() / info.getCellCount());
            islandAvgHeights.put(id, avgHeight);

            // Calculate center point and round to nearest integer
            int centerX = (int) Math.round((double) info.getSumX() / info.getCellCount());
            int centerY = (int) Math.round((double) info.getSumY() / info.getCellCount());
            Point centerPoint = new Point(centerX, centerY);
            islandCenterPoints.put(id, centerPoint);

            // Find the island with the maximum average height
            if (avgHeight > maxAverageHeight) {
                maxAverageHeight = avgHeight;
                islandWithMaxAvgHeightId = id;
            }
        }

        return new IslandData(islandIdsMatrix, islandAvgHeights, islandWithMaxAvgHeightId, mapData, islandCenterPoints);
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
        info.addCoordinates(i, j); // Accumulate coordinates

        // Visit all four adjacent cells
        dfs(i - 1, j, mapData, islandIds, islandId, info); // Up
        dfs(i + 1, j, mapData, islandIds, islandId, info); // Down
        dfs(i, j - 1, mapData, islandIds, islandId, info); // Left
        dfs(i, j + 1, mapData, islandIds, islandId, info); // Right
    }

}
