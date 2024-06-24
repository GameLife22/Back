package fr.sqli.formation.gamelife.configuration;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import fr.sqli.formation.gamelife.service.AuthenticationDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${jwt.public.key}")
    private RSAPublicKey key;

    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    private final AuthenticationDetailsService authenticationDetailsService;

    private static final String ADMIN = "SCOPE_ROLE_ADMIN";
    private static final String SELLER = "SCOPE_ROLE_REVENDEUR";
    private static final String BUYER = "SCOPE_ROLE_ACHETEUR";


    @Autowired
    public SecurityConfiguration(AuthenticationDetailsService pAuthenticationDetailsService) {
        authenticationDetailsService = pAuthenticationDetailsService;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationDetailsService pAuthenticationDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(pAuthenticationDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/utilisateur/auth").permitAll()
                .requestMatchers("/utilisateur/*").permitAll()
                .requestMatchers("/utilisateur/mdpoublie").permitAll()
                .requestMatchers("/utilisateur/mdpreset").permitAll()
                .requestMatchers("/utilisateur/getEmailByToken").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/games").hasAnyAuthority(ADMIN, SELLER)
                .requestMatchers(HttpMethod.GET, "/api/v1/games/genres").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.GET, "/api/v1/games/platforms").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.GET,"/api/v1/games/search/{name}").hasAnyAuthority(ADMIN)
                .requestMatchers(HttpMethod.GET,"/api/v1/games/{id}").hasAnyAuthority(ADMIN, SELLER)
                .requestMatchers(HttpMethod.POST, "/api/v1/games").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.PATCH,"/api/v1/games").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.DELETE,"/api/v1/games/{id}").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.GET,"/api/v1/games/genres").hasAuthority(ADMIN)
                .requestMatchers(HttpMethod.GET,"/api/v1/games/platforms").hasAuthority(ADMIN)
                .requestMatchers("/inscription/inscription").permitAll()
                .requestMatchers("/inscription/siret").permitAll()
                .requestMatchers("/inscription/activer").permitAll()
                .requestMatchers("/inscription/validation").permitAll()
                .requestMatchers("/commande/all").hasAuthority(BUYER)
                .requestMatchers("/commande/creer").permitAll()
                .requestMatchers("/commande/{idCommande}").permitAll()
                .requestMatchers("/commande/{idCommande}/modif-quantite").permitAll()
                .requestMatchers("/commande/{idCommande}/prix-total").permitAll()
                .requestMatchers("/commande/{idCommande}/ajout-produit").permitAll()
                .requestMatchers("/commande/{idCommande}/valider-commande").permitAll()
                .requestMatchers("/commande/{idCommande}/supp-article/{idProduit}").permitAll()
                .anyRequest().authenticated()
        )
        .oauth2ResourceServer((pOAuth2ResourceServerConfigurer) -> pOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .userDetailsService(authenticationDetailsService);
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(key).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        var jwk =  new RSAKey.Builder(key).privateKey(priv).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    /* Encode password */
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    /* Cors */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8100"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Content-Type");
        configuration.addExposedHeader("Accept");
        configuration.addExposedHeader("Origin");
        configuration.addExposedHeader("Access-Control-Request-Method");
        configuration.addExposedHeader("Access-Control-Request-Headers");
        configuration.addExposedHeader("Access-Control-Allow-Origin");
        configuration.addExposedHeader("Access-Control-Allow-Credentials");
        configuration.addExposedHeader("Access-Control-Allow-Headers");
        configuration.addExposedHeader("Access-Control-Allow-Methods");
        configuration.addExposedHeader("Access-Control-Max-Age");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}