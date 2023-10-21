package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.IdDto;
import fr.sqli.formation.gamelife.dto.login.LoginRequest;
import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDto;
import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDtoHandler;
import fr.sqli.formation.gamelife.service.UtilisateurService;
import fr.sqli.formation.gamelife.service.authentication.TokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurControler {
    @Autowired
    private UtilisateurService service;
    @Autowired
    private TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private static final Logger LOG = LogManager.getLogger();

    public UtilisateurControler(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public String token(@RequestBody LoginRequest userLogin) throws Exception {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }

    @PostMapping("/infos")
    public UtilisateurDto getInfos(@RequestBody IdDto id){
        LOG.info("InscriptionControler : IN {}", id);
        UtilisateurDto res;
        try {
            res =UtilisateurDtoHandler.fromEntity(service.getUtilisateurById(id.getId()));
            LOG.info("InscriptionControler : OUT {}", res);
            return res;
        } catch (Exception err){
            LOG.warn("ERROR : {}",err);
        }
        //res = UtilisateurDtoHandler.fromEntity(service.getUtilisateurById(id));

        return null;
    }

}
