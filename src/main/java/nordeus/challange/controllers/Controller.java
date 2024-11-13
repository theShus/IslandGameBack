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
@CrossOrigin()
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

}

