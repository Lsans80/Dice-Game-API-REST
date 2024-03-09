package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.GameDTO;

import java.util.List;

public interface GameService {
    GameDTO playGame (long playerId, String username);
    List<GameDTO> getAllGames (long playerId, String username);
    void deleteAllGames (long playerId, String username);
}
