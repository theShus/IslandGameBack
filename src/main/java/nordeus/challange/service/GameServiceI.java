package nordeus.challange.service;


import nordeus.challange.models.CommandResponse;

public interface GameServiceI {

    CommandResponse<int[][]> getIslandData();
}
