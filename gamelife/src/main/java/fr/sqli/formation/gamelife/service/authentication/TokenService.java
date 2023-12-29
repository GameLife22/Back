package fr.sqli.formation.gamelife.service.authentication;


import fr.sqli.formation.gamelife.ex.CompteDesactiveException;
import fr.sqli.formation.gamelife.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import org.springframework.security.core.GrantedAuthority;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder encoder;
    private static final Logger LOG = LogManager.getLogger();
    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    private UtilisateurService utilisateurService;

    public String generateToken(Authentication authentication) throws Exception {
        if(utilisateurService.getUtilistateurByEmail(authentication.getName()).getEmail() == null){
            throw new CompteDesactiveException("Compte desactive");
        }
        String id = String.valueOf(utilisateurService.getUtilistateurByEmail(authentication.getName()).getId());

        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .id(id)
                .claim("scope", scope)
                .build();


        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


}
