package nordeus.cnallange.service;


import nordeus.cnallange.models.CommandResponse;

public interface GameServiceI {

    CommandResponse<int[][]> getIslandData();
}
