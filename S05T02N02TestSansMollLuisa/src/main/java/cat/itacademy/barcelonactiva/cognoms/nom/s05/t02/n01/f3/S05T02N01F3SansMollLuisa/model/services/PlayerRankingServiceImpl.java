package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.PlayerNotFound;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces.PlayerRankingService;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.utils.PlayerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayerRankingServiceImpl implements PlayerRankingService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public double getSuccessRateAverage (){
        List<Player> players = playerRepository.findAll();
        if(players.isEmpty()){
            throw new PlayerNotFound("Players not found in the system.");
        }
        double totalSuccessRate = players.stream()
                .mapToDouble(Player::calculateSuccessRate)
                .sum();
        return Math.round(totalSuccessRate / players.size());
    }

    @Override
    public PlayerDTO getWinner (){
        List<Player> players = playerRepository.findAll();
        if(players.isEmpty()){
            throw new PlayerNotFound("Players not found in the system.");
        }
        Player winner = players.stream().max(Comparator.comparing(Player::calculateSuccessRate))
                .orElse(null);
        return PlayerConverter.EntitytoDTO(winner);
    }

    @Override
    public PlayerDTO getLoser (){
        List<Player> players = playerRepository.findAll();
        if(players.isEmpty()){
            throw new PlayerNotFound("Players not found in the system.");
        }
        Player loser = players.stream().min(Comparator.comparing(Player::calculateSuccessRate))
                .orElse(null);
        return PlayerConverter.EntitytoDTO(loser);
    }
}
