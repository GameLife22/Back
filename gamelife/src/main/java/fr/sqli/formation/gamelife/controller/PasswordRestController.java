package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.response.EmailResponse;
import fr.sqli.formation.gamelife.utility.converter.IPasswordConverter;
import fr.sqli.formation.gamelife.dto.request.PasswordRequest;
import fr.sqli.formation.gamelife.dto.request.ResetPasswordRequest;
import fr.sqli.formation.gamelife.entity.UserEntity;
import fr.sqli.formation.gamelife.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilisateur")

public class PasswordRestController {


    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordRestController.class);

    @PostMapping("/mdpoublie")
    public void mdpoublie(@RequestBody PasswordRequest monbody) throws Exception{
        LOGGER.info("MdpOublieControler : IN {}", monbody);

        userService.mdpOublie(monbody);
        LOGGER.info("MdpOublieControler : OUT ");
    }

    @GetMapping("/getEmailByToken")
    public ResponseEntity<EmailResponse>  getEmailByToken(@RequestParam String token )  {
        var user = this.userService.getByResetPasswordToken(token);
        if (user.equals(null)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        EmailResponse email = IPasswordConverter.fromEntity(user);

        return ResponseEntity.ok(email);
    }

    @PostMapping("/mdpreset")
    public void mdpreset(@RequestBody ResetPasswordRequest monbody, @RequestParam String token) {
        LOGGER.info("MdpResetControler : IN {} {}", token,monbody.getPwd());
        UserEntity user = userService.getByResetPasswordToken(token);
        userService.modifierMotDePasse(user,monbody);
        LOGGER.info("MdpResetControler : OUT ");
    }

}
