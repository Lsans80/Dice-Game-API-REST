package cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;
    @Column
    private LocalDate gameDate;
    @Column
    private int dice1;
    @Column
    private int dice2;
    @Column
    private boolean won;
    @ManyToOne
    @JoinColumn(name = "playerId", referencedColumnName = "playerId")
    private Player player;

    public boolean isWon() {
        return dice1 + dice2 == 7;
    }
}
