package nordeus.cnallange.controllers;

import nordeus.cnallange.businessLogic.GameBL;
import nordeus.cnallange.businessLogic.GameBLI;
import nordeus.cnallange.models.CommandResponse;
import nordeus.cnallange.models.IslandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Controller {

    private final GameBLI _businessLogic;

    @Autowired
    public Controller(GameBL businessLogic) {
        this._businessLogic = businessLogic;
    }

    @GetMapping("/getGameData")
    public CommandResponse<IslandData> helloWorld() {
        return _businessLogic.getCalculatedGameData();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> serviceCheck() {
        return ResponseEntity.ok().build();
    }
}