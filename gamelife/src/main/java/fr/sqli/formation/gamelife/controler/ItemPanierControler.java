package fr.sqli.formation.gamelife.controler;


import fr.sqli.formation.gamelife.dto.itemPanier.ItemPanierDtoHandler;
import fr.sqli.formation.gamelife.dto.itemPanier.ItemPanierDtoIn;
import fr.sqli.formation.gamelife.dto.itemPanier.ItemPanierDtoOut;
import fr.sqli.formation.gamelife.service.ItemPanierService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ItemPanier")
public class ItemPanierControler {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private  ItemPanierService ItemPanierService;


    @PostMapping("/create")
    public void creationItemPanier(@RequestBody ItemPanierDtoIn monbody) throws Exception{
        LOG.info("ItemPanierControler : IN {}", monbody);
        LOG.info("id_Commande : {}",monbody.getId_panier());
        LOG.info("id_Produit : {}",monbody.getId_produit());
        LOG.info("Quantite : {}",monbody.getQuantite());
        ItemPanierService.creerItemPanier(monbody);
        LOG.info("ItemPanierControler : OUT {}", monbody);
    }
    @DeleteMapping("/delete")
    public void suppressionItemPanier(@RequestBody ItemPanierDtoIn monbody) throws Exception {
        LOG.info("ItemPanierControler : IN {}", monbody);
        ItemPanierService.supprimerItemPanier(monbody);
        LOG.info("ItemPanierControler : OUT {}", monbody);
    }

    @PutMapping("/update")
    public void modificationItemPanier(@RequestBody ItemPanierDtoIn monbody) throws Exception {
        LOG.info("ItemPanierControler : IN {}", monbody);
        ItemPanierService.modifierItemPanier(monbody);
        LOG.info("ItemPanierControler : OUT {}", monbody);
    }

   @GetMapping("/get")
    public ResponseEntity<ItemPanierDtoOut>  getItemPanierByPanierIdAndProduitId(@RequestBody ItemPanierDtoOut monbody ) throws Exception {
        var ItemPanier = this.ItemPanierService.getItemPanier(monbody);
       if (ItemPanier.equals(null)) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }

       ItemPanierDtoOut ItemPanierDto = ItemPanierDtoHandler.fromEntity(ItemPanier);

       return ResponseEntity.ok(ItemPanierDto);
   }
}
