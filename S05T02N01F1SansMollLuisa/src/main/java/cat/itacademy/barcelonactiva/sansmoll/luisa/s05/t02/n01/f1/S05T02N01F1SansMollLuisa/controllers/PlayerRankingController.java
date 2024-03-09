package cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.controllers;

import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.services.interfaces.PlayerRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerRankingController {

    @Autowired
    private PlayerRankingService playerRankingService;

    @GetMapping("/ranking")
    public ResponseEntity<?> getSuccessAveragePlayers (){
        Double successAverage = playerRankingService.getSuccessRateAverage();
        return new ResponseEntity<>(successAverage, HttpStatus.OK);
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getWinner (){
        PlayerDTO winner = playerRankingService.getWinner();
        return new ResponseEntity<>(winner, HttpStatus.OK);
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<?> getLoser (){
        PlayerDTO loser = playerRankingService.getLoser();
        return new ResponseEntity<>(loser, HttpStatus.OK);
    }

}
