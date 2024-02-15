package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.inscription.InscriptionDto;
import fr.sqli.formation.gamelife.dto.inscription.InscriptionDtoHandler;

import fr.sqli.formation.gamelife.dto.mdpOublie.EmailDtoOut;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import net.bytebuddy.utility.RandomString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InscriptionService {
    @Autowired
    private UtilisateurRepository uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private EmailService service;
    private static final Logger LOG = LogManager.getLogger();

    public UtilisateurEntity inscription(InscriptionDto dto) throws Exception{
        LOG.info("Inscription - {}", dto.getEmail());
        UtilisateurEntity.validate(dto.getNom(),dto.getPrenom(), dto.getMdp(), dto.getEmail(),dto.getVille(),dto.getNum_rue(),dto.getRue(), dto.getNum_siret(), dto.getCode_postal());
            var newUser = uDao.findByEmail(dto.getEmail());
            String token = RandomString.make(30);
            if(newUser.isEmpty()){
                UtilisateurEntity u = InscriptionDtoHandler.fromDto(dto);
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
        LOG.info(response);

        return true;
    }
    */

    public void validateAccount(EmailDtoOut dto) throws Exception {
        var email = dto.getLogin();
        LOG.info("validateAccount - {}", email);
        if (email == null) {
            LOG.error("validateAccount - null?");
            throw new IllegalArgumentException("email est null !");
        }
        if (email.trim().isEmpty()) {
            LOG.error("validateAccount - \"\"?");
            throw new IllegalArgumentException("email est vide !");
        }
        var result = this.uDao.findByEmail(email);
        if (result.isPresent()) {
            var user = result.get();
            if (!user.getEtatCompte()) {
                LOG.info("validateAccount - found user with id {}", user.getId());


                String resetPasswordLink = "http://localhost:4200/activationcompte?token=" + user.getResetPasswordToken();

                this.service.sendEmailValidationInscription(email,resetPasswordLink);
                return;
            }
            LOG.warn("validateAccount - {}, Status {}", email, user.getEtatCompte());

        }else {
            LOG.warn("validateAccount - No user found with email={}", email);
            throw new UtilisateurNonExistantException("Utilisateur introuvable");
        }

    }
    public void activateAccount(String token){
        UtilisateurEntity u = uDao.findByResetPasswordToken(token);
        u.setEtatCompte(true);
        u.setResetPasswordToken(null);
        uDao.save(u);
    }
}
