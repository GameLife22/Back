package fr.sqli.formation.gamelife.service.utilisateur;

import fr.sqli.formation.gamelife.dto.inscription.InscriptionDto;
import fr.sqli.formation.gamelife.dto.inscription.InscriptionDtoHandler;
import fr.sqli.formation.gamelife.dto.mdpOublie.EmailDtoOut;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurExistantException;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.dao.IUtilisateurDao;
import fr.sqli.formation.gamelife.service.authentification.EmailService;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InscriptionService {
    @Autowired
    private IUtilisateurDao uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private EmailService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(InscriptionService.class);

    public UtilisateurEntite inscription(InscriptionDto dto) throws Exception{
        LOGGER.info("Inscription - {}", dto.getEmail());
        UtilisateurEntite.validate(dto.getNom(),dto.getPrenom(), dto.getMdp(), dto.getEmail(),dto.getVille(),dto.getNum_rue(),dto.getRue(), dto.getNum_siret(), dto.getCode_postal());
            var newUser = uDao.findByEmail(dto.getEmail());
            String token = RandomString.make(30);
            if(newUser.isEmpty()){
                UtilisateurEntite u = InscriptionDtoHandler.fromDto(dto);
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
                    throw new UtilisateurExistantException("Utilisateur deja enregistre");
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

    public void validateAccount(EmailDtoOut dto) throws Exception {
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
            throw new UtilisateurNonExistantException("Utilisateur introuvable");
        }

    }
    public void activateAccount(String token){
        UtilisateurEntite u = uDao.findByResetPasswordToken(token);
        u.setEtatCompte(true);
        u.setResetPasswordToken(null);
        uDao.save(u);
    }
}
