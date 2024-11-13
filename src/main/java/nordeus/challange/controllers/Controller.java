package nordeus.challange.controllers;

import nordeus.challange.businessLogic.GameBL;
import nordeus.challange.businessLogic.GameBLI;
import nordeus.challange.models.CommandResponse;
import nordeus.challange.models.IslandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/islandGame")
@CrossOrigin() // Apply CORS to this controller only
public class Controller {

    private final GameBLI _businessLogic;

    @Autowired
    public Controller(GameBL businessLogic) {
        this._businessLogic = businessLogic;
    }

    @GetMapping("/getData")
    public CommandResponse<IslandData> helloWorld() {
        return _businessLogic.getCalculatedGameData();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> serviceCheck() {
        return ResponseEntity.ok().build();
    }


    public static void printMatrixDouble(double[][] matrix, int decimalPlaces) {
        if (matrix == null || matrix.length == 0) {
            System.out.println("Empty matrix.");
            return;
        }

        // Prepare the format for each cell based on the specified decimal places
        String format = "%." + decimalPlaces + "f";

        // Find the maximum width needed for each cell, considering the decimal formatting
        int maxWidth = 0;
        for (double[] row : matrix) {
            for (double value : row) {
                String formattedValue = String.format(format, value);
                int cellWidth = formattedValue.length();
                if (cellWidth > maxWidth) {
                    maxWidth = cellWidth;
                }
            }
        }

        // Print the matrix with each cell padded to the max width
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%" + maxWidth + "." + decimalPlaces + "f ", value); // Right-align each cell
            }
            System.out.println();
        }
    }

    public void printMatrixInt(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            System.out.println("Empty matrix.");
            return;
        }

        // Find the maximum width needed for each cell
        int maxWidth = 0;
        for (int[] row : matrix) {
            for (int value : row) {
                int cellWidth = String.valueOf(value).length();
                if (cellWidth > maxWidth) {
                    maxWidth = cellWidth;
                }
            }
        }

        // Print the matrix with each cell padded to the max width
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.printf("%" + maxWidth + "d ", value); // Right-align each cell
            }
            System.out.println();
        }
    }

    public void printMatrixId(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            System.out.println("Empty matrix.");
            return;
        }

        // Find the maximum width needed for each cell
        int maxWidth = 0;
        for (int[] row : matrix) {
            for (int value : row) {
                int cellWidth = String.valueOf(value).length();
                if (cellWidth > maxWidth) {
                    maxWidth = cellWidth;
                }
            }
        }

        // Print the matrix with each cell padded to the max width
        for (int[] row : matrix) {
            for (int value : row) {
                if (value == 0) System.out.printf("-  ");
                else System.out.printf("%" + maxWidth + "d  ", value); // Right-align each cell
            }
            System.out.println();
        }
    }

}

