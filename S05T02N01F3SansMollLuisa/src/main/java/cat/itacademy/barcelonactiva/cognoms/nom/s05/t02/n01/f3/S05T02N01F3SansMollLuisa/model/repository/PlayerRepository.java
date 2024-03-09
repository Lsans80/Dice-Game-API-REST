package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository <Player, Long> {
    Optional<Player> findByName (String name);
    boolean existsByNameIgnoreCase(String name);
    List<Player> findByUser_UserId(long userId);
    Player findByUser_UserIdAndPlayerId(long userId, long playerId);


}
