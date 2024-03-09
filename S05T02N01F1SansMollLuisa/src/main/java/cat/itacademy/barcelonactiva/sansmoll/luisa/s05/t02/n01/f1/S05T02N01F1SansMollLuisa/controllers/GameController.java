package cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.controllers;

import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.services.interfaces.GameService;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.services.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;

    @PostMapping("/{id}/games")
    public ResponseEntity<?> playGame (@PathVariable long id){
        GameDTO gameDTO = gameService.playGame(id);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }
    @GetMapping("/{id}/games")
    public ResponseEntity<?> getAllGames (@PathVariable long id){
        List<GameDTO> gameDTOList = gameService.getAllGames(id);
        return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
    }
    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteAllGames (@PathVariable long id){
        gameService.deleteAllGames(id);
        return new ResponseEntity<>("Games have been deleted.", HttpStatus.OK);
    }
}
