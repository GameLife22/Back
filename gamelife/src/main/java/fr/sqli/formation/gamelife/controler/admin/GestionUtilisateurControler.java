package fr.sqli.formation.gamelife.controler.admin;
import fr.sqli.formation.gamelife.dto.admin.gestionUtilisateur.UsersDtoHandler;
import fr.sqli.formation.gamelife.dto.admin.gestionUtilisateur.UsersDtoOut;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.GestionCompteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/users")

public class GestionUtilisateurControler {
    @Autowired
    private GestionCompteService service;

    private static final Logger LOG = LogManager.getLogger();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UsersDtoOut>> getAllUsers() {
        LOG.info("Dans getAllUsers Admin");
        var users = service.getUsers();
        var rd = new ArrayList<UsersDtoOut>();
        for (UtilisateurEntity e : users) {
            rd.add(UsersDtoHandler.fromEntity(e));
        }
        LOG.info("Sortie de getAllProduit avec {} produits",users.size());
        return ResponseEntity.ok(rd);
    }

}
