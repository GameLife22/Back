package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.Id;
import fr.sqli.formation.gamelife.dto.request.LoginRequest;
import fr.sqli.formation.gamelife.dto.UserRegistration;
import fr.sqli.formation.gamelife.utility.converter.IUserRegistrationConverter;
import fr.sqli.formation.gamelife.service.UserService;
import fr.sqli.formation.gamelife.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateur")
public class UserRestController {
    @Autowired
    private UserService service;
    @Autowired
    private TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    public UserRestController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public String token(@RequestBody LoginRequest userLogin) throws Exception {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));
        return tokenService.generateToken(authentication);
    }

    @PostMapping("/infos")
    public UserRegistration getInfos(@RequestBody Id id){
        LOGGER.info("InscriptionControler : IN {}", id);
        UserRegistration res;
        try {
            res = IUserRegistrationConverter.fromEntity(service.getUtilisateurById(id.getId()));
            LOGGER.info("InscriptionControler : OUT {}", res);
            return res;
        } catch (Exception err){
            LOGGER.warn("ERROR : {}",err);
        }
        //res = UtilisateurDtoHandler.fromEntity(service.getUtilisateurById(id));

        return null;
    }

}
