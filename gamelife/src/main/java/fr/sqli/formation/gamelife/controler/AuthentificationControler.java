package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.LoginDto;
import fr.sqli.formation.gamelife.dto.UtilisateurDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthentificationControler {

        @Autowired
        private AuthentificationService service;


        //http://localhost:8080/auth/env1?pL=fabien.bidault@social.aston-ecole.com&pP=Paz6!!3
        @GetMapping("/env1")
        public String auth01(@RequestParam("pL") String login ,@RequestParam("pP") String pwd) throws Exception{

            if(login != null && !login.trim().isEmpty() && pwd!= null && !pwd.trim().isEmpty()){
                UtilisateurEntity resu;
                resu = this.service.authentifier(login,pwd);
                return String.valueOf(resu.getId());
            }
            else {
                throw new IllegalArgumentException("Login ou password vide ou null");
            }
        }

        @PostMapping("/env3")
        public String auth03(@RequestBody LoginDto monBody) throws Exception {
            if(monBody.getLogin() != null && !monBody.getLogin().trim().isEmpty() && monBody.getPwd()!= null && !monBody.getPwd().trim().isEmpty()){
                UtilisateurEntity resu;
                resu = this.service.authentifier(monBody.getLogin(), monBody.getPwd());
                return String.valueOf(resu.getId());
            }
            else {
                throw new IllegalArgumentException("Login ou password vide ou null");
            }
        }

    @PostMapping("/env4")
    public UtilisateurDto auth04(@RequestBody LoginDto monBody) throws Exception{

        if(monBody.getLogin() != null && !monBody.getLogin().trim().isEmpty() && monBody.getPwd()!= null && !monBody.getPwd().trim().isEmpty()){
            UtilisateurEntity resu;
            var dto = new UtilisateurDto();
            resu = this.service.authentifier(monBody.getLogin(), monBody.getPwd());
            dto.setId(resu.getId());
            dto.setNom(resu.getNom());
            dto.setPrenom(resu.getPrenom());
            return dto;
        }
        else {
            throw new IllegalArgumentException("Login ou password vide ou null");
        }
    }

    @PostMapping("/env5")
    public ResponseEntity<UtilisateurDto> auth05(@RequestBody LoginDto monBody) throws Exception{

        if(monBody.getLogin() != null && !monBody.getLogin().trim().isEmpty() && monBody.getPwd()!= null && !monBody.getPwd().trim().isEmpty()){
            UtilisateurEntity resu;
            var dto = new UtilisateurDto();
            resu = this.service.authentifier(monBody.getLogin(), monBody.getPwd());
            dto.setId(resu.getId());
            dto.setNom(resu.getNom());
            dto.setPrenom(resu.getPrenom());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        else {
            throw new IllegalArgumentException("Login ou password vide ou null");
        }
    }

}
