package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.utils;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerConverter {

    public static Player DTOtoEntity(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setPlayerId(playerDTO.getPlayerId());
        player.setName(playerDTO.getName());
        player.setRegisterDate(playerDTO.getRegisterDate());
        return player;
    }

    public static PlayerDTO EntitytoDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerId(player.getPlayerId());
        playerDTO.setName(player.getName());
        playerDTO.setRegisterDate(player.getRegisterDate());
        playerDTO.setSuccessRate(player.calculateSuccessRate());
        return playerDTO;
    }
}
