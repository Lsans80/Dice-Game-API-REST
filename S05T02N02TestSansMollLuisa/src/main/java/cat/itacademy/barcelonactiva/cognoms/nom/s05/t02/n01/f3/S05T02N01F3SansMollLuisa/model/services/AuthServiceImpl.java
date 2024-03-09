package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.exceptions.UserAlreadyExists;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.Role;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.domain.User;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.AuthResponse;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.AuthRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.dto.UserDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.model.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    //RegisterRequest = UserDTO
    @Override
    public AuthResponse register(UserDTO userDTO) {
        if(userAlreadyExists(userDTO.getName())){
            throw new UserAlreadyExists("User already exists.");
        }
        var user = User.builder()
                .name(userDTO.getName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtServiceImpl.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getName(),
                        authRequest.getPassword()
                )
        );
        var user = userRepository.findUserByName(authRequest.getName()).orElseThrow();
        var jwToken = jwtServiceImpl.generateToken(user);
        return AuthResponse.builder().token(jwToken).build();
    }

    private boolean userAlreadyExists(String username) {
        return userRepository.existsByNameIgnoreCase(username);
    }

}
