package fr.sqli.formation.gamelife.service.produit;

import fr.sqli.formation.gamelife.convertisseur.IProduitRevendeurConvertisseur;
import fr.sqli.formation.gamelife.dao.IProduitRevendeurDao;
import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurReponse;
import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurRequete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class ProduitRevendeurService implements IProduitRevendeurService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduitRevendeurRequete.class);

    IProduitRevendeurDao produitRevendeurDao;

    @Autowired
    ProduitRevendeurService(IProduitRevendeurDao pIProduitRevendeurDao) {
        produitRevendeurDao = pIProduitRevendeurDao;
    }

    @Override
    public ProduitRevendeurReponse recupererProduitRevendeur(UUID pProduitRevendeurId) {
        LOGGER.info("Tentative de récupération d'un produit revendeur avec l'identifiant : {}", pProduitRevendeurId);
        ProduitRevendeurReponse produitRevendeur = IProduitRevendeurConvertisseur.convertirEnCategorieReponse(this.produitRevendeurDao.findById(pProduitRevendeurId)
                .orElseThrow(() -> new EntityNotFoundException("Aucun produit revendeur trouvé avec l'identifiant fourni : " + pProduitRevendeurId)));
        LOGGER.info("Produit revendeur récupéré avec succès : {}", produitRevendeur);
        return produitRevendeur;
    }
}
