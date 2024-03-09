package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services.interfaces;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {
    PlayerDTO addPlayer (PlayerDTO player);
    PlayerDTO getOnePlayer (String playerId);
    List<PlayerDTO> getAllPlayers ();
    PlayerDTO updatePlayer (String playerId, PlayerDTO newPlayerDTO);
    void deletePlayer(String id);
}
