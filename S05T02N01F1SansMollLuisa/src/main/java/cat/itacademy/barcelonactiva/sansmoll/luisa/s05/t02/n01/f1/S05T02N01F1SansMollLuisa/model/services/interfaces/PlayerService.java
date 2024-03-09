package cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.services.interfaces;

import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.dto.PlayerDTO;

import java.util.List;

public interface PlayerService {
    PlayerDTO addPlayer (PlayerDTO player);
    PlayerDTO getOnePlayer (long playerId);
    List<PlayerDTO> getAllPlayers ();
    PlayerDTO updatePlayer (long playerId, PlayerDTO newPlayerDTO);
    void deletePlayer(long id);
}
