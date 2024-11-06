package nordeus.cnallange.businessLogic;

import nordeus.cnallange.models.CommandResponse;
import nordeus.cnallange.models.IslandData;

public interface GameBLI {

    CommandResponse<IslandData> getCalculatedGameData();
}
