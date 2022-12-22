package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.login.LoginDtoHandler;
import fr.sqli.formation.gamelife.dto.login.LoginDtoIn;
import fr.sqli.formation.gamelife.dto.mdpOublie.MdpOublieDtoIn;
import fr.sqli.formation.gamelife.dto.resetMdp.resetMdpDtoIn;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.ex.CompteDesactiveException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import net.bytebuddy.utility.RandomString;
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

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


@Service
public class AuthentificationService implements AuthenticationProvider {

    private static final Logger LOG = LogManager.getLogger();
    @Autowired
    private UtilisateurRepository uDao;

    @Autowired
    private EmailService emailService;


    @Autowired
    private BCryptPasswordEncoder encoder;


    private UtilisateurEntity authentifier(LoginDtoIn dto) throws Exception {
        if (dto.getLogin() != null && !dto.getLogin().trim().isEmpty() && dto.getPwd() != null && !dto.getPwd().trim().isEmpty() ) {
            var monUser = uDao.findByEmail(dto.getLogin());

            if (monUser.isPresent()) {
                if(monUser.get().getEtatCompte() == 1){
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
        LoginDtoIn login = new LoginDtoIn(email,password) ;

        AuthentificationService.LOG.info("Spring Security Authenticate email={}", email);

        UtilisateurEntity user = null;
        try {
            user = authentifier(login);
        } catch (Exception lExp) {
            throw new AuthenticationServiceException("Erreur d'authentification", lExp);
        }
        if (user != null) {
            AuthentificationService.LOG.info("Spring Security Authenticate found {} {}", user.getEmail(),user.getRole());
            Collection<GrantedAuthority> springSecurityRoles = new ArrayList<>(1);

            //Inserer le role du user dans le token
            GrantedAuthority ga = new SimpleGrantedAuthority(user.getRole());
            springSecurityRoles.add(ga);

            var upat = new UsernamePasswordAuthenticationToken(email, password, springSecurityRoles);
            //Les détails qu'on veut insérer dans notre token
            upat.setDetails(LoginDtoHandler.fromEntity(user));
            return upat;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Authentication.class.isAssignableFrom(authentication);

    }

    public void mdpOublie(MdpOublieDtoIn dto) throws UtilisateurNonExistantException, CompteDesactiveException, MessagingException, UnsupportedEncodingException {
        var pEmail = dto.getLogin();
        AuthentificationService.LOG.debug("forgotPassword - {}", pEmail);
        if (pEmail == null) {
            AuthentificationService.LOG.error("forgotPassword - null?");
            throw new IllegalArgumentException("email est null !");
        }
        if (pEmail.trim().isEmpty()) {
            AuthentificationService.LOG.error("forgotPassword - \"\"?");
            throw new IllegalArgumentException("email est vide !");
        }
        var result = this.uDao.findByEmail(pEmail);
        if (result.isPresent()) {
            var user = result.get();
            if (user.getEtatCompte() == 1) {
                AuthentificationService.LOG.debug("forgotPassword - found user with id {}", user.getId());
                String token = RandomString.make(30);

                this.modifierResetPasswordToken(token,pEmail);

                String resetPasswordLink = "http://localhost:4200/resetmotdepasse?token=" + token;

                this.emailService.sendEmail(pEmail,resetPasswordLink);
                return;
            }
            AuthentificationService.LOG.warn("forgotPassword - {}, Status {}", pEmail, user.getEtatCompte());
            throw new CompteDesactiveException("Compte Desactive");
        }
        AuthentificationService.LOG.warn("forgotPassword - No user found with email={}", pEmail);
        throw new UtilisateurNonExistantException("Utilisateur introuvable");
    }


    public void modifierResetPasswordToken(String token, String email) throws UtilisateurNonExistantException {
        Optional<UtilisateurEntity> user = uDao.findByEmail(email);

        if (user != null) {
            user.get().setResetPasswordToken(token);
            uDao.save(user.get());
        } else {
            throw new UtilisateurNonExistantException("Utilisateur Non Existant {} " + email);
        }
    }

    public UtilisateurEntity getByResetPasswordToken(String token) {
        return uDao.findByResetPasswordToken(token);
    }

    public void modifierMotDePasse(UtilisateurEntity user, resetMdpDtoIn dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        LOG.info("MDP : {}",dto.getPwd());
        String encodedPassword = passwordEncoder.encode(dto.getPwd());
        LOG.info("MDP Encoded : {}",encodedPassword);
        user.setMdp(encodedPassword);
        user.setResetPasswordToken(null);
        uDao.save(user);
    }
}
