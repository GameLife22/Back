package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.mdpOublie.EmailDtoOut;
import fr.sqli.formation.gamelife.dto.mdpOublie.MdpOublieDtoHandler;
import fr.sqli.formation.gamelife.dto.mdpOublie.MdpOublieDtoIn;
import fr.sqli.formation.gamelife.dto.resetMdp.resetMdpDtoIn;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import fr.sqli.formation.gamelife.service.utilisateur.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateur")

public class MdpOublieControleur {


    @Autowired
    private UtilisateurService utilisateurService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MdpOublieControleur.class);

    @PostMapping("/mdpoublie")
    public void mdpoublie(@RequestBody MdpOublieDtoIn monbody) throws Exception{
        LOGGER.info("MdpOublieControler : IN {}", monbody);

        utilisateurService.mdpOublie(monbody);
        LOGGER.info("MdpOublieControler : OUT ");
    }

    @GetMapping("/getEmailByToken")
    public ResponseEntity<EmailDtoOut>  getEmailByToken(@RequestParam String token )  {
        var user = this.utilisateurService.getByResetPasswordToken(token);
        if (user.equals(null)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        EmailDtoOut email = MdpOublieDtoHandler.fromEntity(user);

        return ResponseEntity.ok(email);
    }

    @PostMapping("/mdpreset")
    public void mdpreset(@RequestBody resetMdpDtoIn monbody, @RequestParam String token) {
        LOGGER.info("MdpResetControler : IN {} {}", token,monbody.getPwd());
        UtilisateurEntite user = utilisateurService.getByResetPasswordToken(token);
        utilisateurService.modifierMotDePasse(user,monbody);
        LOGGER.info("MdpResetControler : OUT ");
    }

}
