package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.PlayerAlreadyExists;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.UserNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.User;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces.PlayerService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.converter.PlayerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public PlayerDTO addPlayer(PlayerDTO playerDTO, String username) {
        User user = userRepository.findUserByName(username)
                .orElseThrow(() -> new UserNotFound("User not found: " + username));

        if (playerDTO.getName() == null || playerDTO.getName().isBlank() || playerDTO.getName().equalsIgnoreCase("Unknown")){
            playerDTO.setName("UNKNOWN");
        } else if(playerRepository.existsByNameIgnoreCase(playerDTO.getName())) {
            throw new PlayerAlreadyExists("Ups! Player already exists.");
        }
        return setNewPlayer(playerDTO, user);
    }

    private PlayerDTO setNewPlayer (PlayerDTO playerDTO, User user){

        playerDTO.setName(playerDTO.getName());
        playerDTO.setRegisterDate(LocalDateTime.now());
        Player player = PlayerConverter.DTOtoEntity(playerDTO);
        player.setUser(user);
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
    public List<PlayerDTO> getAllPlayers(String username) {
        long userId = findUserById(username);
        List<Player> players = playerRepository.findByUser_UserId(userId);

        if(players.isEmpty()){
            throw new PlayerNotFound("You do not have any players. Create new ones!.");
        }
        return players.stream()
                .map(PlayerConverter::EntitytoDTO)
                .collect(Collectors.toList());
    }
    @Override
    public PlayerDTO updatePlayer(long playerId, PlayerDTO newPlayerDTO, String username) {
        long userId = findUserById(username);
        Player playerToUpdate = playerRepository.findByUser_UserIdAndPlayerId(userId,playerId);
        if(playerToUpdate == null){
            throw new PlayerNotFound("You do not have any player with this ID.");
        }


        if (newPlayerDTO.getName() == null || newPlayerDTO.getName().isBlank()){
            newPlayerDTO.setName("UNKNOWN");
        } else if(playerRepository.existsByNameIgnoreCase(newPlayerDTO.getName())) {
            throw new PlayerAlreadyExists("Ups! Player already exists.");
        }
        playerToUpdate.setName(newPlayerDTO.getName());
        playerRepository.save(playerToUpdate);
        return PlayerConverter.EntitytoDTO(playerToUpdate);
    }

    public long findUserById(String username){
        User user = userRepository.findUserByName(username).orElseThrow(() -> new UserNotFound("User not found."));
        return user.getUserId();
    }

    @Override
    public void deletePlayer (long playerId, String username){
        long userId = findUserById(username);
        Player playerToDelete = playerRepository.findByUser_UserIdAndPlayerId(userId, playerId);
        if(playerToDelete == null){
            throw new PlayerNotFound("You do not have any player with this ID.");
        }
        playerRepository.deleteById(playerId);
    }
}
