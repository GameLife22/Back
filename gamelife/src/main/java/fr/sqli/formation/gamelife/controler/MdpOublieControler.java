package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.mdpOublie.EmailDtoOut;
import fr.sqli.formation.gamelife.dto.mdpOublie.MdpOublieDtoHandler;
import fr.sqli.formation.gamelife.dto.mdpOublie.MdpOublieDtoIn;
import fr.sqli.formation.gamelife.dto.resetMdp.resetMdpDtoIn;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// TODO : rename file
@RestController
@RequestMapping("/utilisateur")

public class MdpOublieControler {


    @Autowired
    private UtilisateurService utilisateurService;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("/mdpoublie")
    public void mdpoublie(@RequestBody MdpOublieDtoIn monbody) throws Exception{
        LOG.info("MdpOublieControler : IN {}", monbody);

        utilisateurService.mdpOublie(monbody);
        LOG.info("MdpOublieControler : OUT ");
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
        LOG.info("MdpResetControler : IN {} {}", token,monbody.getPwd());
        UtilisateurEntity user = utilisateurService.getByResetPasswordToken(token);
        utilisateurService.modifierMotDePasse(user,monbody);
        LOG.info("MdpResetControler : OUT ");
    }

}
