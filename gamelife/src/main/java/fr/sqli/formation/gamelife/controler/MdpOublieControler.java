package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.mdpOublie.MdpOublieDtoIn;
import fr.sqli.formation.gamelife.dto.restMdp.resetMdpDtoIn;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.AuthentificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class MdpOublieControler {


    @Autowired
    private AuthentificationService authentificationService;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("/mdpoublie")
    public void mdpoublie(@RequestBody MdpOublieDtoIn monbody) throws Exception{
        LOG.info("MdpOublieControler : IN {}", monbody);
        authentificationService.mdpOublie(monbody);
        LOG.info("MdpOublieControler : OUT ");
    }

    @PostMapping("/mdpreset")
    public void mdpreset(@RequestBody resetMdpDtoIn monbody, @RequestParam String token) throws Exception{
        LOG.info("MdpResetControler : IN {} {}", token,monbody.getPwd());
        UtilisateurEntity user = authentificationService.getByResetPasswordToken(token);
        authentificationService.modifierMotDePasse(user,monbody);
        LOG.info("MdpResetControler : OUT ");
    }

}
