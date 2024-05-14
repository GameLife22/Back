package fr.sqli.formation.gamelife.service.produitRevendeur;

import fr.sqli.formation.gamelife.dao.IProduitDao;
import fr.sqli.formation.gamelife.dao.IProduitRevendeurDao;
import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurReponse;
import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurRequete;
import fr.sqli.formation.gamelife.entite.ProduitEntite;
import fr.sqli.formation.gamelife.entite.ProduitRevendeurEntite;
import fr.sqli.formation.gamelife.service.produit.ProduitService;
import fr.sqli.formation.gamelife.service.utilisateur.GestionCompteService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ProduitRevendeurService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduitRevendeurService.class);
    @Autowired
    private IProduitRevendeurDao produitRevendeurDao;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private GestionCompteService gestionCompteService;
    private ProduitRevendeurEntite produitRevendeurEntite;


    public ProduitRevendeurEntite ajouterProduitRevendeur(ProduitRevendeurRequete pProduitRevendeurRequete) throws Exception {
        ProduitRevendeurEntite produitRevendeurEntite = new ProduitRevendeurEntite();
        //<TODO> : check if the stock is not negative, prix is not negative and etat is not null
        produitRevendeurEntite.setStock(pProduitRevendeurRequete.getStock());
        produitRevendeurEntite.setPrix(pProduitRevendeurRequete.getPrix());
        produitRevendeurEntite.setEtat(pProduitRevendeurRequete.getEtat());
        produitRevendeurEntite.setProduit(produitService.recupererProduit(pProduitRevendeurRequete.getIdProduit()));
        produitRevendeurEntite.setUtilisateur(gestionCompteService.getUser(pProduitRevendeurRequete.getIdUtilisateur()));

        return produitRevendeurDao.save(produitRevendeurEntite);
    }

    public ProduitRevendeurEntite modifierProduitRevendeur(UUID pProduitRevendeurID, ProduitRevendeurRequete pProduitRequete) {
        Optional<ProduitRevendeurEntite> optionalProduitRevendeurEntite = produitRevendeurDao.findById(pProduitRevendeurID);
        if (optionalProduitRevendeurEntite.isEmpty()) {
            LOGGER.error("Produit revendeur non trouvé");
            return null; // or throw an exception
        }

        ProduitRevendeurEntite produitRevendeurEntite = optionalProduitRevendeurEntite.get();
        //<TODO> : check if the stock is not negative, prix is not negative and etat is not null
        produitRevendeurEntite.setStock(pProduitRequete.getStock());
        produitRevendeurEntite.setPrix(pProduitRequete.getPrix());
        produitRevendeurEntite.setEtat(pProduitRequete.getEtat());
        produitRevendeurEntite.setProduit(produitService.recupererProduit(pProduitRequete.getIdProduit()));

        produitRevendeurDao.save(produitRevendeurEntite);

        return produitRevendeurEntite;
    }

    public void supprimerProduitRevendeur(UUID produitRevendeurID) {
    }

    public ProduitRevendeurEntite recupererProduitRevendeur() throws Exception{
        UUID id = UUID.fromString("63ef0498-3148-4e57-a4f6-4c17a9ed9352");
        Optional<ProduitRevendeurEntite> produitRevendeurEntite = produitRevendeurDao.findById(id);
        if (produitRevendeurEntite.isPresent()) {
            return produitRevendeurEntite.get();
        }
        else {
            LOGGER.error("Produit revendeur non trouvé");
            throw new Exception("Produit revendeur non trouvé");
       }
    }

    public List<ProduitRevendeurEntite> recupererProduitsRevendeur() {
        return produitRevendeurDao.findAll();
    }
}
