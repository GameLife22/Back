package fr.sqli.formation.gamelife.controler;


import fr.sqli.formation.gamelife.dto.panier.PanierDtoIn;
import fr.sqli.formation.gamelife.dto.panier.PanierDtoOut;
import fr.sqli.formation.gamelife.service.PanierService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/panier")
public class PanierControler {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private  PanierService panierService;


    @PostMapping("/create")
    public void creationPanier(@RequestBody PanierDtoIn monbody) throws Exception{
        LOG.info("PanierControler : IN {}", monbody);
        LOG.info("id_Commande : {}",monbody.getId_commande());
        LOG.info("id_Produit : {}",monbody.getId_produit());
        LOG.info("Quantite : {}",monbody.getQuantite());
        panierService.creerPanier(monbody);
        LOG.info("PanierControler : OUT {}", monbody);
    }
    @DeleteMapping("/delete")
    public void suppressionPanier(@RequestBody PanierDtoIn monbody) throws Exception {
        LOG.info("PanierControler : IN {}", monbody);
        panierService.supprimerPanier(monbody);
        LOG.info("PanierControler : OUT {}", monbody);
    }

    @PutMapping("/update")
    public void modificationPanier(@RequestBody PanierDtoIn monbody) throws Exception {
        LOG.info("PanierControler : IN {}", monbody);
        panierService.modifierPanier(monbody);
        LOG.info("PanierControler : OUT {}", monbody);
    }

   /* @GetMapping("/get")
    public ResponseEntity<List<PanierDtoOut>>*/

}
