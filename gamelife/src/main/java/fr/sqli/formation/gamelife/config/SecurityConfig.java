package fr.sqli.formation.gamelife.config;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import fr.sqli.formation.gamelife.service.authentication.AuthDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    protected Environment env;

    private static final Logger LOG = LogManager.getLogger();

    private final RsaKeyProperties rsaKeys;
    private final AuthDetailsService authDetailsService;

    public SecurityConfig(RsaKeyProperties rsaKeys, AuthDetailsService authDetailsService) {
        this.rsaKeys = rsaKeys;
        this.authDetailsService = authDetailsService;
    }

    @Bean
    public AuthenticationManager authManager(AuthDetailsService authDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        final var h2Url = env.getProperty("spring.h2.console.path", "/h2");
        return web -> web.ignoring().requestMatchers(new AntPathRequestMatcher(h2Url + "/**"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        LOG.debug("SpringSecurityConfigurationSecured - Apply rules");
        final var h2Url = env.getProperty("spring.h2.console.path", "/h2");
        LOG.debug("SpringSecurityConfigurationSecured - H2 console is on {}", h2Url);
        http.csrf(AbstractHttpConfigurer::disable).cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher(h2Url + "/**") // h2
                ).permitAll()).headers(header -> {
                    header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                }).authorizeHttpRequests(auth -> auth
                        .antMatchers("/utilisateur/auth").permitAll()
                        .antMatchers("/utilisateur/id").permitAll()
                        .antMatchers("/utilisateur/mdpoublie").permitAll()
                        .antMatchers("/utilisateur/mdpreset").permitAll()
                        .antMatchers("/utilisateur/getEmailByToken").permitAll()
                        .antMatchers("/produit/{id}").permitAll()
                        .antMatchers("/produit/all").permitAll()
                        .antMatchers("/produit/add").permitAll()
                        .antMatchers("/produit/update").permitAll()
                        .antMatchers("/produit/delete/{id}").permitAll()
                        .antMatchers("/produit/search").permitAll() //todo: context search
                        .antMatchers("/inscription/inscription").permitAll()
                        .antMatchers("/inscription/siret").permitAll()
                        .antMatchers("/inscription/activer").permitAll()
                        .antMatchers("/inscription/validation").permitAll()
                        .antMatchers("/h2-console").permitAll()
                        .antMatchers("/commande/all").permitAll()
                        .antMatchers("/commande/creer").permitAll()
                        .antMatchers("/commande/{id}").permitAll()
                        .antMatchers("/commande/{id}/modif-quantite").permitAll()
                        .antMatchers("/commande/{id}/prix-total").permitAll()
                        .antMatchers("/commande/{id}/ajout-article").permitAll()
                        .antMatchers("/commande/{id}/valider-commande").permitAll()
                        .antMatchers("/commande/{idPanier}/supp-article/{idProduit}").permitAll()
                        .anyRequest().authenticated()

                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(authDetailsService);
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey())
                .privateKey(rsaKeys.privateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
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
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
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