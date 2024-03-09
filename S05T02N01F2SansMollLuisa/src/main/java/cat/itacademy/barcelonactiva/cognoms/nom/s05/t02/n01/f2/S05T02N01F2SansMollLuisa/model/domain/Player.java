package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "player")
public class Player {
    @Id
    private String playerId;
    private String name;
    private LocalDateTime registerDate;
    //@DBRef(lazy = false)
    private List<Game> games;

    public void addGame (Game game){
        if(games == null){
            games = new ArrayList<>();
        }
        games.add(game);
        game.setPlayer(this);
    }

    public float calculateSuccessRate (){
        if(games != null && !games.isEmpty()){
            long totalGames = games.size();
            long wonGames = games.stream().filter(Game::isWon).count();
            return (float) wonGames/totalGames * 100;
        }
        return 0.0f;
    }

}
