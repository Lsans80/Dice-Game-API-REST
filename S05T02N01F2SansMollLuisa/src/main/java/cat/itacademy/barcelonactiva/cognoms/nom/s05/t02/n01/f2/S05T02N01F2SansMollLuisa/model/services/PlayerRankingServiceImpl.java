package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services.interfaces.PlayerRankingService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.utils.PlayerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayerRankingServiceImpl implements PlayerRankingService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;

    @Transactional
    @Override
    public double getSuccessRateAverage (){
        List<Player> players = playerRepository.findAll();
        if(players.isEmpty()){
            throw new PlayerNotFound("Players not found in the system.");
        }

        return players.stream()
                .mapToDouble(Player::calculateSuccessRate)
                .average().orElse(0.0);
    }

    @Override
    public PlayerDTO getWinner (){
        List<Player> players = playerRepository.findAll();
        if(players.isEmpty()){
            throw new PlayerNotFound("Not players found.");
        }
        Player winner = players.stream().max(Comparator.comparing(Player::calculateSuccessRate))
                .orElse(null);
        return PlayerConverter.EntitytoDTO(winner);
    }

    @Override
    public PlayerDTO getLoser (){
        List<Player> players = playerRepository.findAll();
        if(players.isEmpty()){
            throw new PlayerNotFound("Not players found.");
        }
        Player loser = players.stream().min(Comparator.comparing(Player::calculateSuccessRate))
                .orElse(null);
        return PlayerConverter.EntitytoDTO(loser);
    }
}
