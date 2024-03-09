package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.utils;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.GameDTO;
import org.springframework.stereotype.Component;

@Component
public class GameConverter {
    public static GameDTO EntityToDTO (Game game){
        GameDTO gameDTO = new GameDTO();
        gameDTO.setGameId(game.getGameId());
        gameDTO.setDice1(game.getDice1());
        gameDTO.setDice2(game.getDice2());
        gameDTO.setWon(game.isWon());
        return gameDTO;
    }
}
