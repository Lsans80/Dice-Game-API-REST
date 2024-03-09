package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.controllers;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerDTO> addPlayer (@RequestBody PlayerDTO playerDTO){
        PlayerDTO newPlayer = playerService.addPlayer(playerDTO);
        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer (@PathVariable String id, @RequestBody PlayerDTO newPlayerDTO){
        PlayerDTO playerToUpdate = playerService.updatePlayer(id,newPlayerDTO);
        return new ResponseEntity<>(playerToUpdate,HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers (){
        List<PlayerDTO> players = playerService.getAllPlayers();
        return new ResponseEntity<>(players,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer (@PathVariable String id){
        playerService.deletePlayer(id);
        return new ResponseEntity<>("Player deleted.",HttpStatus.OK);
    }

}
