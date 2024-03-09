package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services.interfaces;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.GameDTO;

import java.util.List;

public interface GameService {
    GameDTO playGame (String playerId);
    List<GameDTO> getAllGames (String playerId);
    void deleteAllGames (String playerId);
}
