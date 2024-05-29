package fr.sqli.formation.gamelife.service.produitRevendeur;

import com.sun.xml.bind.v2.TODO;
import fr.sqli.formation.gamelife.convertisseur.IProduitConvertisseur;
import fr.sqli.formation.gamelife.dao.IProduitRevendeurDao;
import fr.sqli.formation.gamelife.dto.produit.ProduitReponse;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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


    public ProduitRevendeurEntite ajouterProduitRevendeur(ProduitRevendeurRequete pProduitRevendeurRequete) throws Exception {
        ProduitRevendeurEntite produitRevendeurEntite = new ProduitRevendeurEntite();
        if(pProduitRevendeurRequete.getStock() <= 0 ){
            throw new Exception("Stock incorrect");
        }else {
            produitRevendeurEntite.setStock(pProduitRevendeurRequete.getStock());
        }
        if(pProduitRevendeurRequete.getPrix().compareTo(BigDecimal.ZERO) <= 0 ){
            throw new Exception("Prix incorrect");
        }else {
            produitRevendeurEntite.setPrix(pProduitRevendeurRequete.getPrix());
        }
        if(pProduitRevendeurRequete.getEtat() == null){
            throw new Exception("Etat incorrect");
        } else{
                produitRevendeurEntite.setEtat(pProduitRevendeurRequete.getEtat());
        }
        produitRevendeurEntite.setProduit(IProduitConvertisseur.entityFromDtoOut(produitService.recupererProduit(pProduitRevendeurRequete.getIdProduit())));
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

        produitRevendeurDao.save(produitRevendeurEntite);

        return produitRevendeurEntite;
    }

    public void supprimerProduitRevendeur(UUID produitRevendeurID) {
        produitRevendeurDao.deleteById(produitRevendeurID);
    }


    public ProduitRevendeurEntite recupererProduitRevendeur(UUID produitRevendeurID) {
        Optional<ProduitRevendeurEntite> produitRevendeurEntite = produitRevendeurDao.findById(produitRevendeurID);
        if (produitRevendeurEntite.isPresent()) {
            return produitRevendeurEntite.get();
        }
        else {
            LOGGER.error("Produit revendeur non trouvé");
            return null;
        }
    }

    public List<ProduitRevendeurEntite> recupererProduitsRevendeurUtilisateur(UUID utilisateurID) throws Exception {
        List<ProduitRevendeurEntite> produitRevendeurEntite = produitRevendeurDao.findAllByUtilisateur(utilisateurID);
        LOGGER.info("Get recupererProduitsRevendeurUtilisateur products: " );
        if (produitRevendeurEntite != null) {
            return produitRevendeurEntite;
        }
        else {
            LOGGER.error("Produit revendeur non trouvé");
            return null;
        }
    }
    public List<ProduitEntite> getAllProduitByRevendeur(UUID revendeurId) throws Exception {


        // Retrieve all products
        List<ProduitEntite> allProducts = IProduitConvertisseur.entitiesFromDtoOut(produitService.recupererProduits());
        LOGGER.info("Get all products: " );
        // Retrieve all products that the reseller already has
        List<ProduitEntite> revendeurProducts = recupererProduitsRevendeurUtilisateur(revendeurId)
                .stream()
                .map(ProduitRevendeurEntite::getProduit)
                .toList();

        // Subtract the list of products that the reseller already has from the list of all products

        return allProducts.stream()
                .filter(product -> revendeurProducts.stream().noneMatch(revendeurProduct -> revendeurProduct.getId().equals(product.getId())))
                .collect(Collectors.toList());
    }

    public List<ProduitRevendeurEntite> recupererProduitsRevendeur() {
        return produitRevendeurDao.findAll();
    }
}
