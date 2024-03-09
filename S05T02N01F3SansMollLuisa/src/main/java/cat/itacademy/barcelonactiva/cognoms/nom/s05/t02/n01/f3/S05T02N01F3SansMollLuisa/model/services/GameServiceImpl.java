package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.GameNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces.GameService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.converter.GameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerServiceImpl playerService;
    @Autowired
    private PlayerRepository playerRepository;

    @Transactional //Makes sure all the requests are successful.
    @Override
    public GameDTO playGame(long playerId, String username) {
        long userid = playerService.findUserById(username);
        Player player = playerRepository.findByUser_UserIdAndPlayerId(userid, playerId);
        if(player == null){
            throw new PlayerNotFound("You do not have any player with this ID.");
        }

        Game newGame = new Game();
        newGame.setPlayer(player);
        rollDice(newGame);
        newGame.setGameDate(LocalDate.now());
        player.addGame(newGame);
        gameRepository.save(newGame);

        return GameConverter.EntityToDTO(newGame);
    }

    private void rollDice (Game game){
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        game.setDice1(dice1);
        game.setDice2(dice2);
        game.setWon(game.isWon());
    }

    @Transactional
    @Override
    public List<GameDTO> getAllGames(long playerId, String username) {
        long userid= playerService.findUserById(username);
        Player player = playerRepository.findByUser_UserIdAndPlayerId(userid,playerId);
        if(player == null){
            throw new PlayerNotFound("You do not have any player with this ID.");
        }

        List<Game> games = gameRepository.findByPlayer_PlayerId(player.getPlayerId());
        if(games==null || games.isEmpty()){
            throw new GameNotFound("There are no games for this player, Play!!.");
        }
        return games.stream().map(GameConverter::EntityToDTO).collect(Collectors.toList());
    }
    @Transactional
    public void deleteAllGames (long playerId, String username) {
        long userid= playerService.findUserById(username);
        Player player = playerRepository.findByUser_UserIdAndPlayerId(userid,playerId);
        if(player == null){
            throw new PlayerNotFound("You do not have any player with this ID.");
        }

        List<Game> games = player.getGames();
        if(games==null || games.isEmpty()){
            throw new GameNotFound("There are no games for this player, sorry.");
        }
        games.clear();
        gameRepository.deleteByPlayer_PlayerId(player.getPlayerId());
    }

//    //Not working! The reason seems to be that there is a bidirectional relation between Player/Game,
//    therefore, we need to delete the reference to games first and after that, save again the player with no Games
//    or delete the List of Games.

//    @Transactional
//    public void deleteAllGames(long playerId) {
//        gameRepository.deleteByPlayer_PlayerId(playerId);
//    }
}
