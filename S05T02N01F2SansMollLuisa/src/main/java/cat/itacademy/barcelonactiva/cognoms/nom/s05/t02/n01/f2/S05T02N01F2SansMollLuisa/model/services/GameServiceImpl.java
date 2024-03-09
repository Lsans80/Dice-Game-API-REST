package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.exceptions.GameNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services.interfaces.GameService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.utils.GameConverter;
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
    private PlayerRepository playerRepository;

    @Override
    public GameDTO playGame(String playerId) {
        Player player = findPlayerById(playerId);

        Game newGame = new Game();
        newGame.setPlayer(player);
        rollDice(newGame);
        newGame.setGameDate(LocalDate.now());
        newGame = gameRepository.save(newGame);
        player.addGame(newGame);
        playerRepository.save(player);


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
    public List<GameDTO> getAllGames(String playerId) {
        Player player = findPlayerById(playerId);
        List<Game> games = gameRepository.findByPlayer_PlayerId(player.getPlayerId());
        if(games==null || games.isEmpty()){
            throw new GameNotFound("There is no games for this player, sorry.");
        }
        return games.stream().map(GameConverter::EntityToDTO).collect(Collectors.toList());
    }
    @Transactional
    public void deleteAllGames (String playerId) {
        Player player = findPlayerById(playerId);
        List<Game> games = gameRepository.findByPlayer_PlayerId(player.getPlayerId());

        if(games==null || games.isEmpty()){
            throw new GameNotFound("There is no games for this player, sorry.");
        }
        games.clear();
        gameRepository.deleteByPlayer_PlayerId(player.getPlayerId());
    }

    public Player findPlayerById (String playerId){
        return playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFound("Player not found."));
    }

//    //Not working! The reason seems to be that there is a bidirectional relation between Player/Game,
//    therefore, we need to delete the reference to games first and after that, save again the player with no Games
//    or delete the List of Games.

//    @Transactional
//    public void deleteAllGames(long playerId) {
//        gameRepository.deleteByPlayer_PlayerId(playerId);
//    }
}
