package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.mdpOublie.MdpOublieDtoIn;
import fr.sqli.formation.gamelife.dto.resetMdp.resetMdpDtoIn;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.CompteDesactiveException;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import net.bytebuddy.utility.RandomString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository repository;

    @Autowired
    private EmailService emailService;

    private static final Logger LOG = LogManager.getLogger();

    public UtilisateurEntity getUtilisateurById(UUID id) throws Exception{
        var utilisateur = this.repository.findById(id);

        if(utilisateur.isPresent()){
            return utilisateur.get();
        }else{
            throw new UtilisateurExistantException();
        }
    }


    public UtilisateurEntity getUtilistateurByEmail(String email) throws Exception{
        if(email != null && !email.isEmpty()){
            var utilisateur = this.repository.findByEmail(email);

            if(utilisateur.isPresent()){
                return utilisateur.get();
            }
            else{
                throw new UtilisateurExistantException();
            }
        }
        throw new IllegalArgumentException();

    }
    public void mdpOublie(MdpOublieDtoIn dto) throws UtilisateurNonExistantException, CompteDesactiveException, MessagingException, UnsupportedEncodingException {
        var pEmail = dto.getLogin();
        UtilisateurService.LOG.debug("forgotPassword - {}", pEmail);
        if (pEmail == null) {
            UtilisateurService.LOG.error("forgotPassword - null?");
            throw new IllegalArgumentException("email est null !");
        }
        if (pEmail.trim().isEmpty()) {
            UtilisateurService.LOG.error("forgotPassword - \"\"?");
            throw new IllegalArgumentException("email est vide !");
        }
        var result = this.repository.findByEmail(pEmail);
        if (result.isPresent()) {
            var user = result.get();
            if (user.getEtatCompte()) {
                UtilisateurService.LOG.debug("forgotPassword - found user with id {}", user.getId());
                String token = RandomString.make(30);

                this.modifierResetPasswordToken(token,pEmail);

                String resetPasswordLink = "http://localhost:4200/resetmotdepasse?token=" + token;

                this.emailService.sendEmail(pEmail,resetPasswordLink);
                return;
            }
            UtilisateurService.LOG.warn("forgotPassword - {}, Status {}", pEmail, user.getEtatCompte());
            throw new CompteDesactiveException("Compte Desactive");
        }
        UtilisateurService.LOG.warn("forgotPassword - No user found with email={}", pEmail);
        throw new UtilisateurNonExistantException("Utilisateur introuvable");
    }


    public void modifierResetPasswordToken(String token, String email) throws UtilisateurNonExistantException {
        Optional<UtilisateurEntity> user = repository.findByEmail(email);

        if (user != null) {
            user.get().setResetPasswordToken(token);
            repository.save(user.get());
        } else {
            throw new UtilisateurNonExistantException("Utilisateur Non Existant {} " + email);
        }
    }

    public UtilisateurEntity getByResetPasswordToken(String token) {
        return repository.findByResetPasswordToken(token);
    }

    public void modifierMotDePasse(UtilisateurEntity user, resetMdpDtoIn dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        LOG.info("MDP : {}",dto.getPwd());
        String encodedPassword = passwordEncoder.encode(dto.getPwd());
        LOG.info("MDP Encoded : {}",encodedPassword);
        user.setMdp(encodedPassword);
        user.setResetPasswordToken(null);
        repository.save(user);
    }
}
