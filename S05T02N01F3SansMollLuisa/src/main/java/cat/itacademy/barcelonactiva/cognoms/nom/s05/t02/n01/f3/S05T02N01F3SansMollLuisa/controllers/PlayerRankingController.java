package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.controllers;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces.PlayerRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players/ranking")
@RequiredArgsConstructor

public class PlayerRankingController {

    @Autowired
    private PlayerRankingService playerRankingService;

    @GetMapping
    public ResponseEntity<?> getSuccessAveragePlayers (){
        Double successAverage = playerRankingService.getSuccessRateAverage();
        return new ResponseEntity<>(successAverage, HttpStatus.OK);
    }

    @GetMapping("/winner")
    public ResponseEntity<?> getWinner (){
        PlayerDTO winner = playerRankingService.getWinner();
        return new ResponseEntity<>(winner, HttpStatus.OK);
    }

    @GetMapping("/loser")
    public ResponseEntity<?> getLoser (){
        PlayerDTO loser = playerRankingService.getLoser();
        return new ResponseEntity<>(loser, HttpStatus.OK);
    }

}
