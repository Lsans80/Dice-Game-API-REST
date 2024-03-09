package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.services.interfaces;


import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.model.dto.PlayerDTO;

public interface PlayerRankingService {

    double getSuccessRateAverage ();
    PlayerDTO getWinner ();
    PlayerDTO getLoser ();
}
