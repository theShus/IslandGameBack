package nordeus.challange.businessLogic;

import nordeus.challange.models.CommandResponse;
import nordeus.challange.models.IslandData;

public interface GameBLI {

    CommandResponse<IslandData> getCalculatedGameDataSmooth();

    CommandResponse<IslandData> test();
}
