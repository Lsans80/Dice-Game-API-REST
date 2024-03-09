package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.UserNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.User;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.PlayerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplTest {
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PlayerServiceImpl playerService;
    private User user;
    private PlayerDTO playerDTO;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setupEntities() {
        //Mock existing User before each test
        user = new User();
        user.setUserId(1L);
        user.setName("userName");

        //Mock existing Player before each test
        playerDTO = new PlayerDTO();
        playerDTO.setName("playerName");

        player1 = new Player();
        player1.setPlayerId(1l);
        player1.setName("player1");
        player1.setRegisterDate(LocalDateTime.now());
        player1.setGames(new ArrayList<>());

        player2 = new Player();
        player2.setPlayerId(2l);
        player2.setName("player2");
        player2.setRegisterDate(LocalDateTime.now());
        player2.setGames(new ArrayList<>());

    }

    @AfterEach
    public void removeEntities(){
        user = null;
        playerDTO = null;
        player1 = null;
        player2 = null;
    }

    @DisplayName("Test add player by user")
    @Test
    public void testAddPlayer() {
        when(userRepository.findUserByName("userName")).thenReturn(Optional.of(user));

        // Simulates a non existing Player.
        when(playerRepository.existsByNameIgnoreCase("playerName")).thenReturn(false);

        // Llama al método que deseas probar
        PlayerDTO result = playerService.addPlayer(playerDTO, "userName");

        // Verifica que se llamaron los métodos adecuados y que el resultado es el esperado
        verify(playerRepository, times(1)).save(any());
        assertNotNull(result);
        assertEquals("playerName", result.getName());
    }

    @DisplayName("Test get all players by user")
    @Test
    public void testGetAllPlayers_ExistingPlayers() {
        when(userRepository.findUserByName("userName")).thenReturn(Optional.of(user));

        List<Player> players = new ArrayList<>();
        players.add(new Player(player1.getPlayerId(),player1.getName(),player1.getRegisterDate(), player1.getGames(), player1.getUser()));
        players.add(new Player(player2.getPlayerId(),player2.getName(),player2.getRegisterDate(), player2.getGames(), player2.getUser()));
        when(playerRepository.findByUser_UserId(user.getUserId())).thenReturn(players);

        // Call the method
        List<PlayerDTO> result = playerService.getAllPlayers("userName");

        // Verify mocks and assertions
        verify(userRepository).findUserByName("userName");
        verify(playerRepository).findByUser_UserId(user.getUserId());
        assertEquals(2, result.size());

    }

    @DisplayName("Test update player by user")
    @Test
    public void testUpdatePlayer_Success() {
        when(userRepository.findUserByName("userName")).thenReturn(Optional.of(user));

        //Set the new name to our playerDTO
        String newPlayerName = "NewName";
        playerDTO.setName(newPlayerName);

        Player existingPlayer = new Player();
        existingPlayer.setName(playerDTO.getName());

        when(playerRepository.findByUser_UserIdAndPlayerId(user.getUserId(), playerDTO.getPlayerId())).thenReturn(existingPlayer);
        when(playerRepository.existsByNameIgnoreCase(newPlayerName)).thenReturn(false);
        when(userRepository.findUserByName("userName")).thenReturn(Optional.of(user));

        // Call the method
        PlayerDTO result = playerService.updatePlayer(playerDTO.getPlayerId(), playerDTO, "userName");

        // Verify mocks and assertions
        verify(playerRepository, times(1)).findByUser_UserIdAndPlayerId(user.getUserId(), playerDTO.getPlayerId());
        verify(playerRepository, times(1)).save(existingPlayer);
        assertNotNull(result);
        assertEquals(newPlayerName, result.getName());
    }

    @DisplayName("Test delete player by user")
    @Test
    public void testDeletePlayer_Success() {
        when(userRepository.findUserByName("userName")).thenReturn(Optional.of(user));

        // Existance of the id provided
        Player playerToDelete = new Player();
        when(playerRepository.findByUser_UserIdAndPlayerId(user.getUserId(), player1.getPlayerId())).thenReturn(playerToDelete);

        // Call the method
        playerService.deletePlayer(player1.getPlayerId(), "userName");

        // Verify mocks and assertions
        verify(userRepository, times(1)).findUserByName("userName");
        verify(playerRepository, times(1)).findByUser_UserIdAndPlayerId(user.getUserId(), player1.getPlayerId());
        verify(playerRepository, times(1)).deleteById(player1.getPlayerId());
    }
}
