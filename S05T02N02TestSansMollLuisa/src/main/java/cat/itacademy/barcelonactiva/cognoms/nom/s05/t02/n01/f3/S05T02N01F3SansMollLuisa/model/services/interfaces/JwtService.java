package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserDetails userDetails);
    String getUserName(String token);
    boolean validateToken(String token, UserDetails userDetails);
}
