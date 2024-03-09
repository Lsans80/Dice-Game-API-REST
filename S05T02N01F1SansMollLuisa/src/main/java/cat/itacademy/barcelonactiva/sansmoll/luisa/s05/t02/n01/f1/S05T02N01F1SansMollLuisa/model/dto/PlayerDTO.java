package cat.itacademy.barcelonactiva.sansmoll.luisa.s05.t02.n01.f1.S05T02N01F1SansMollLuisa.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private long playerId;
    private String name;
    private LocalDateTime registerDate;
    private float successRate;

}
