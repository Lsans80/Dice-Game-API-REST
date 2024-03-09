package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.exceptions.PlayerAlreadyExists;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services.interfaces.PlayerService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.utils.PlayerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameServiceImpl gameService;
    @Override
    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        if (playerDTO.getName() == null || playerDTO.getName().isBlank() || playerDTO.getName().equalsIgnoreCase("Unknown")){
            playerDTO.setName("UNKNOWN");
        } else if(playerRepository.existsByNameIgnoreCase(playerDTO.getName())) {
            throw new PlayerAlreadyExists("Ups! Player already exists.");
        }
        return setNewPlayer(playerDTO);
    }

    private PlayerDTO setNewPlayer (PlayerDTO playerDTO){

        playerDTO.setName(playerDTO.getName());
        playerDTO.setRegisterDate(LocalDateTime.now());
        Player player = PlayerConverter.DTOtoEntity(playerDTO);
        playerRepository.save(player);
        return PlayerConverter.EntitytoDTO(player);
    }

    @Override
    public PlayerDTO getOnePlayer(String playerId) {
        Player playerExists = playerRepository.findById(playerId).orElse(null);

        if(playerExists == null){
            throw new PlayerNotFound("Player not found.");
        }
        return PlayerConverter.EntitytoDTO(playerExists);
    }

    @Transactional
    @Override
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        if (players.isEmpty()) {
            throw new PlayerNotFound("No players in the system.");
        }

        return players.stream()
                .map(player -> {
                    List<Game> loadedGames = gameRepository.findByPlayer_PlayerId(player.getPlayerId());
                    player.setGames(loadedGames);
                    PlayerDTO playerDTO = PlayerConverter.EntitytoDTO(player);
                    return playerDTO;
                })
                .collect(Collectors.toList());
    }

    //This method does not work bc list of games is always null.
    // It seems the @DBref on "List<Game> games;" in Player does not bring the games in every Player.
//
//    public List<PlayerDTO> getAllPlayers() {
//        List<Player> players = playerRepository.findAll();
//        if(players.isEmpty()){
//            throw new PlayerNotFound("No players in the system.");
//        }
//        return players.stream()
//                .map(PlayerConverter::EntitytoDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public PlayerDTO updatePlayer(String playerId, PlayerDTO newPlayerDTO) {
        Player playerToUpdate = playerRepository.findById(playerId).orElse(null);

        if(playerToUpdate == null) {
            throw new PlayerNotFound("Player not found.");
        } else if (newPlayerDTO.getName() == null || newPlayerDTO.getName().isBlank()){
            newPlayerDTO.setName("UNKNOWN");
        } else if(playerRepository.existsByNameIgnoreCase(newPlayerDTO.getName())) {
            throw new PlayerAlreadyExists("Ups! Player already exists.");
        }
        playerToUpdate.setName(newPlayerDTO.getName());
        playerRepository.save(playerToUpdate);
        return PlayerConverter.EntitytoDTO(playerToUpdate);
    }

    @Override
    public void deletePlayer (String playerId){

        playerRepository.deleteById(gameService.findPlayerById(playerId).getPlayerId());
    }
}
