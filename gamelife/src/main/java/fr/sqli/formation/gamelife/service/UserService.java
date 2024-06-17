package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.request.PasswordRequest;
import fr.sqli.formation.gamelife.dto.request.ResetPasswordRequest;
import fr.sqli.formation.gamelife.entity.UserEntity;
import fr.sqli.formation.gamelife.exception.DisableAccountException;
import fr.sqli.formation.gamelife.exception.ExistingUserException;
import fr.sqli.formation.gamelife.exception.NonExistentUserException;
import fr.sqli.formation.gamelife.repository.IUserRepository;
import fr.sqli.formation.gamelife.utility.generator.ISecureTokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private EmailService emailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserEntity getUtilisateurById(UUID id) throws Exception{
        var utilisateur = this.repository.findById(id);

        if(utilisateur.isPresent()){
            return utilisateur.get();
        }else{
            throw new ExistingUserException();
        }
    }


    public UserEntity getUtilistateurByEmail(String email) throws Exception{
        if(email != null && !email.isEmpty()){
            var utilisateur = this.repository.findByEmail(email);

            if(utilisateur.isPresent()){
                return utilisateur.get();
            }
            else{
                throw new ExistingUserException();
            }
        }
        throw new IllegalArgumentException();

    }
    public void mdpOublie(PasswordRequest dto) throws NonExistentUserException, DisableAccountException, MessagingException, UnsupportedEncodingException {
        var pEmail = dto.getLogin();
        UserService.LOGGER.debug("forgotPassword - {}", pEmail);
        if (pEmail == null) {
            UserService.LOGGER.error("forgotPassword - null?");
            throw new IllegalArgumentException("email est null !");
        }
        if (pEmail.trim().isEmpty()) {
            UserService.LOGGER.error("forgotPassword - \"\"?");
            throw new IllegalArgumentException("email est vide !");
        }
        var result = this.repository.findByEmail(pEmail);
        if (result.isPresent()) {
            var user = result.get();
            if (user.getEtatCompte()) {
                UserService.LOGGER.debug("forgotPassword - found user with ID {}", user.getId());
                String token = ISecureTokenGenerator.generateToken(22);

                this.modifierResetPasswordToken(token,pEmail);

                String resetPasswordLink = "http://localhost:4200/resetmotdepasse?token=" + token;

                this.emailService.sendEmail(pEmail,resetPasswordLink);
                return;
            }
            UserService.LOGGER.warn("forgotPassword - {}, Status {}", pEmail, user.getEtatCompte());
            throw new DisableAccountException("Compte Desactive");
        }
        UserService.LOGGER.warn("forgotPassword - No user found with email={}", pEmail);
        throw new NonExistentUserException("Utilisateur introuvable");
    }


    public void modifierResetPasswordToken(String token, String email) throws NonExistentUserException {
        Optional<UserEntity> user = repository.findByEmail(email);

        if (user != null) {
            user.get().setResetPasswordToken(token);
            repository.save(user.get());
        } else {
            throw new NonExistentUserException("Utilisateur Non Existant {} " + email);
        }
    }

    public UserEntity getByResetPasswordToken(String token) {
        return repository.findByResetPasswordToken(token);
    }

    public void modifierMotDePasse(UserEntity user, ResetPasswordRequest dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        LOGGER.info("MDP : {}",dto.getPwd());
        String encodedPassword = passwordEncoder.encode(dto.getPwd());
        LOGGER.info("MDP Encoded : {}",encodedPassword);
        user.setMdp(encodedPassword);
        user.setResetPasswordToken(null);
        repository.save(user);
    }
}
