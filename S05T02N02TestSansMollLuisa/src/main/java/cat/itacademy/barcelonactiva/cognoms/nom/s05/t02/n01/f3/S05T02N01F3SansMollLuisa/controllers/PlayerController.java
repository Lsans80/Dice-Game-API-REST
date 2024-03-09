package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.controllers;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.JwtServiceImpl;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces.PlayerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor

public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO, HttpServletRequest request) {
        String token = jwtService.extractTokenFromRequest(request);// Obtener el token del encabezado de la solicitud
        String username = jwtService.getUserName(token);// Validar y extraer el nombre de usuario del token

        PlayerDTO newPlayer = playerService.addPlayer(playerDTO, username);
        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer (@PathVariable long id, @RequestBody PlayerDTO newPlayerDTO, HttpServletRequest request){
        String token = jwtService.extractTokenFromRequest(request);
        String username = jwtService.getUserName(token);

        PlayerDTO playerToUpdate = playerService.updatePlayer(id,newPlayerDTO, username);
        return new ResponseEntity<>(playerToUpdate,HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers (HttpServletRequest request){
        String token = jwtService.extractTokenFromRequest(request);
        String username = jwtService.getUserName(token);

        List<PlayerDTO> players = playerService.getAllPlayers(username);
        return new ResponseEntity<>(players,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer (@PathVariable long id, HttpServletRequest request){
        String token = jwtService.extractTokenFromRequest(request);
        String username = jwtService.getUserName(token);

        playerService.deletePlayer(id, username);
        return new ResponseEntity<>("Player deleted.",HttpStatus.OK);
    }

}
