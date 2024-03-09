package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f3.S05T02N01F3SansMollLuisa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurtityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(publicEndpoints()).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    ///WhiteList of endpoints.
    private RequestMatcher publicEndpoints(){
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/auth/**"),
                new AntPathRequestMatcher("/players/**"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/swaggerDiceGame"),
                new AntPathRequestMatcher("/v3/api-docs/**"),
                new AntPathRequestMatcher("/swagger-resources/**"),
                new AntPathRequestMatcher("/error")
        );
    }
}
