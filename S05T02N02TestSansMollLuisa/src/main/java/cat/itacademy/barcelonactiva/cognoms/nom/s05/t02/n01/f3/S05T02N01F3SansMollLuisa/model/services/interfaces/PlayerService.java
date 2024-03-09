package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {

    PlayerDTO addPlayer(PlayerDTO playerDTO, String name);
    PlayerDTO getOnePlayer (long playerId);
    List<PlayerDTO> getAllPlayers (String username);
    PlayerDTO updatePlayer (long playerId, PlayerDTO newPlayerDTO, String username);
    void deletePlayer(long id, String username);
}
