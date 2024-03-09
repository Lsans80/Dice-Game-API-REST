package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.controllers;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.GameServiceImpl;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.JwtServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players/{id}/games")
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private GameServiceImpl gameService;
    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping
    public ResponseEntity<?> playGame (@PathVariable long id, HttpServletRequest request){
        String token = jwtService.extractTokenFromRequest(request);
        String username = jwtService.getUserName(token);

        GameDTO gameDTO = gameService.playGame(id,username);
        return new ResponseEntity<>(gameDTO, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllGames (@PathVariable long id, HttpServletRequest request){
        String token = jwtService.extractTokenFromRequest(request);
        String username = jwtService.getUserName(token);

        List<GameDTO> gameDTOList = gameService.getAllGames(id, username);
        return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteAllGames (@PathVariable long id, HttpServletRequest request){
        String token = jwtService.extractTokenFromRequest(request);
        String username = jwtService.getUserName(token);

        gameService.deleteAllGames(id, username);
        return new ResponseEntity<>("Games have been deleted.", HttpStatus.OK);
    }
}
