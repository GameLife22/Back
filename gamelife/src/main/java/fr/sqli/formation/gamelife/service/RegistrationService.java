package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.Registration;
import fr.sqli.formation.gamelife.utility.converter.IRegistrationConverter;
import fr.sqli.formation.gamelife.dto.response.EmailResponse;
import fr.sqli.formation.gamelife.entity.UserEntity;
import fr.sqli.formation.gamelife.exception.ExistingUserException;
import fr.sqli.formation.gamelife.exception.NonExistentUserException;
import fr.sqli.formation.gamelife.repository.IUserRepository;
import fr.sqli.formation.gamelife.utility.generator.ISecureTokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private IUserRepository uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private EmailService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    public UserEntity inscription(Registration dto) throws Exception{
        LOGGER.info("Inscription - {}", dto.getEmail());
        UserEntity.validate(dto.getNom(),dto.getPrenom(), dto.getMdp(), dto.getEmail(),dto.getVille(),dto.getNum_rue(),dto.getRue(), dto.getNum_siret(), dto.getCode_postal());
            var newUser = uDao.findByEmail(dto.getEmail());
            String token = ISecureTokenGenerator.generateToken(22);
            if(newUser.isEmpty()){
                UserEntity u = IRegistrationConverter.fromDto(dto);
                if(dto.getNum_siret() == null ){
                    u.setRole("ROLE_ACHETEUR");
                }else{
                    u.setRole("ROLE_REVENDEUR");
                }
                u.setMdp(encoder.encode(u.getMdp()));
                u.setEtatCompte(false);
                u.setResetPasswordToken(token);
                return uDao.saveAndFlush(u);
            }else {
                if(!newUser.get().getEtatCompte()){
                    newUser.get().setEtatCompte(false);
                    newUser.get().setNom(dto.getNom());
                    newUser.get().setPrenom(dto.getPrenom());
                    newUser.get().setMdp(encoder.encode(dto.getMdp()));
                    newUser.get().setEmail(dto.getEmail());
                    newUser.get().setVille(dto.getVille());
                    newUser.get().setCodePostal(dto.getCode_postal());
                    newUser.get().setRue(dto.getRue());
                    newUser.get().setNumRue(dto.getNum_rue());

                    if( dto.getNum_siret() == null ){
                        newUser.get().setRole("ROLE_ACHETEUR");
                    }else{
                        newUser.get().setRole("ROLE_REVENDEUR");
                    }

                    newUser.get().setNumSiren(dto.getNum_siret());
                    return uDao.saveAndFlush(newUser.get());
                }else{
                    throw new ExistingUserException("Utilisateur deja enregistre");
                }
            }
    }
    /*
    public boolean checkSiret(SirenDto dto){
        String siren = dto.getSiret();
        String url = "https://api.insee.fr/entreprises/sirene/V3/siren="+siren;
        RestTemplate restTemplate = new RestTemplate();

        Object[] response = restTemplate.getForObject(url, Object[].class);
        LOGGER.info(response);

        return true;
    }
    */

    public void validateAccount(EmailResponse dto) throws Exception {
        var email = dto.getLogin();
        LOGGER.info("validateAccount - {}", email);
        if (email == null) {
            LOGGER.error("validateAccount - null?");
            throw new IllegalArgumentException("email est null !");
        }
        if (email.trim().isEmpty()) {
            LOGGER.error("validateAccount - \"\"?");
            throw new IllegalArgumentException("email est vide !");
        }
        var result = this.uDao.findByEmail(email);
        if (result.isPresent()) {
            var user = result.get();
            if (!user.getEtatCompte()) {
                LOGGER.info("validateAccount - found user with id {}", user.getId());


                String resetPasswordLink = "http://localhost:4200/activationcompte?token=" + user.getResetPasswordToken();

                this.service.sendEmailValidationInscription(email,resetPasswordLink);
                return;
            }
            LOGGER.warn("validateAccount - {}, Status {}", email, user.getEtatCompte());

        }else {
            LOGGER.warn("validateAccount - No user found with email={}", email);
            throw new NonExistentUserException("Utilisateur introuvable");
        }

    }
    public void activateAccount(String token){
        UserEntity u = uDao.findByResetPasswordToken(token);
        u.setEtatCompte(true);
        u.setResetPasswordToken(null);
        uDao.save(u);
    }
}
