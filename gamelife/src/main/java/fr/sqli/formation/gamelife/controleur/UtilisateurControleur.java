package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.IdDto;
import fr.sqli.formation.gamelife.dto.login.LoginRequest;
import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDto;
import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDtoHandler;
import fr.sqli.formation.gamelife.entite.SecuriteUtilisateur;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import fr.sqli.formation.gamelife.service.utilisateur.UtilisateurService;
import fr.sqli.formation.gamelife.service.authentification.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurControleur {
    @Autowired
    private UtilisateurService service;
    @Autowired
    private TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurControleur.class);

    public UtilisateurControleur(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/moi")
    public UtilisateurDto getMoi(@AuthenticationPrincipal SecuriteUtilisateur user){
        LOGGER.info("InscriptionControler : IN {}", user);
        UtilisateurDto res;
        try {
            if (user != null) {
                res = UtilisateurDtoHandler.fromEntity(service.getUtilisateurById(user.getId()));
                LOGGER.info("InscriptionControler : OUT {}", res);
                return res;
            } else {
                return null;
            }
        } catch (Exception err){
            LOGGER.warn("ERROR : {}", err);
            // Gérer l'exception
            return null;
        }
    }


    @PostMapping("/auth")
    public String token(@RequestBody LoginRequest userLogin) throws Exception {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }

    @PostMapping("/infos")
    public UtilisateurDto getInfos(@RequestBody IdDto id){
        LOGGER.info("InscriptionControler : IN {}", id);
        UtilisateurDto res;
        try {
            res =UtilisateurDtoHandler.fromEntity(service.getUtilisateurById(id.getId()));
            LOGGER.info("InscriptionControler : OUT {}", res);
            return res;
        } catch (Exception err){
            LOGGER.warn("ERROR : {}",err);
        }
        //res = UtilisateurDtoHandler.fromEntity(service.getUtilisateurById(id));

        return null;
    }

}
