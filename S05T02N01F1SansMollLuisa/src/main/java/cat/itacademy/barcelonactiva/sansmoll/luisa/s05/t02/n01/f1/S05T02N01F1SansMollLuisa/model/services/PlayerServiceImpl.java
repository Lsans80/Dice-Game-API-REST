package cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.services;

import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.exceptions.PlayerAlreadyExists;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.services.interfaces.PlayerService;
import cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.utils.PlayerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

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
    public PlayerDTO getOnePlayer(long playerId) {
        Player playerExists = findPlayerById(playerId);
        return PlayerConverter.EntitytoDTO(playerExists);
    }

    public Player findPlayerById (long playerId){
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFound("Player not found."));
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerRepository.findAll();

        if(players.isEmpty()){
            throw new PlayerNotFound("No players in the system.");
        }
        return players.stream()
                .map(PlayerConverter::EntitytoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerDTO updatePlayer(long playerId, PlayerDTO newPlayerDTO) {
        Player playerToUpdate = findPlayerById(playerId);

        if (newPlayerDTO.getName() == null || newPlayerDTO.getName().isBlank()){
            newPlayerDTO.setName("UNKNOWN");
        } else if(playerRepository.existsByNameIgnoreCase(newPlayerDTO.getName())) {
            throw new PlayerAlreadyExists("Ups! Player already exists.");
        }
        playerToUpdate.setName(newPlayerDTO.getName());
        playerRepository.save(playerToUpdate);
        return PlayerConverter.EntitytoDTO(playerToUpdate);
    }

    @Override
    public void deletePlayer (long playerId){
        Player playerToDelete = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFound("Player not found."));
        playerRepository.deleteById(playerId);
    }
}
