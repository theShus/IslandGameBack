package nordeus.challange.service;

import nordeus.challange.models.CommandResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GameService implements GameServiceI {

    @Value("${game.map.data.url}")
    private String mapDataUrl;

    private final HttpClient httpClient;

    public GameService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public CommandResponse<int[][]> getIslandData() {
        CommandResponse<int[][]> result = new CommandResponse<>();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(mapDataUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                int[][] data = parseMapData(response.body());
                result.setData(data);
            } else {
                result.setSuccess(false);
                result.setMessage("Failed to get response due to " + response.statusCode());
            }
            return result;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    private int[][] parseMapData(String rawData) {
        String[] rows = rawData.trim().split("\n");
        int[][] mapData = new int[rows.length][rows[0].trim().split(" ").length];

        for (int i = 0; i < rows.length; i++) {
            String[] columns = rows[i].trim().split(" ");
            for (int j = 0; j < columns.length; j++) {
                mapData[i][j] = Integer.parseInt(columns[j]);
            }
        }
        return mapData;
    }

}
