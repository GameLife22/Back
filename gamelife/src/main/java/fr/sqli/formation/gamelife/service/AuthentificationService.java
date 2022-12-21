package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.login.LoginDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.ex.CompteDesactiveException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class AuthentificationService implements AuthenticationProvider {

    private static final Logger LOG = LogManager.getLogger();
    @Autowired
    private UtilisateurRepository uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;


    private UtilisateurEntity authentifier(LoginDto dto) throws Exception {
        if (dto.getLogin() != null && !dto.getLogin().trim().isEmpty() && dto.getPwd() != null && !dto.getPwd().trim().isEmpty() ) {
            var monUser = uDao.findByEmail(dto.getLogin());

            if (monUser.isPresent()) {
                if(monUser.get().getEtatCompte().equals(1)){
                    if (encoder.matches(dto.getPwd(), monUser.get().getMdp())) {
                        return monUser.get();
                    } else {
                        //pas ok
                        LOG.info("Utilisateur inconnu");
                        throw new AuthentificationException("Utilisateur inconnu");
                    }
                } else {
                    LOG.info("Compte Desactive");
                    throw new CompteDesactiveException("Compte Desactive");
                }
            } else {
                //pas ok
                LOG.info("Utilisateur inconnu");
                throw new AuthentificationException("Utilisateur inconnu");
            }

        }
        else {
            LOG.info("Login ou password vide ou null");
            throw new IllegalArgumentException("Login ou password vide ou null");
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var email = authentication.getName();
        var password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
        LoginDto login = new LoginDto(email,password) ;

        AuthentificationService.LOG.info("Spring Security Authenticate email={}", email);

        UtilisateurEntity user = null;
        try {
            user = authentifier(login);
        } catch (Exception lExp) {
            throw new AuthenticationServiceException("Erreur d'authentification", lExp);
        }
        if (user != null) {
            AuthentificationService.LOG.info("Spring Security Authenticate found {} {}", user.getId(),user.getRole());
            Collection<GrantedAuthority> springSecurityRoles = new ArrayList<>(1);

            //Inserer le role du user dans le token
            GrantedAuthority ga = new SimpleGrantedAuthority(user.getRole());
            springSecurityRoles.add(ga);

            var upat = new UsernamePasswordAuthenticationToken(email, password, springSecurityRoles);
            //Les détails qu'on veut insérer dans notre token
            upat.setDetails(user.getId());
            return upat;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Authentication.class.isAssignableFrom(authentication);

    }


}
