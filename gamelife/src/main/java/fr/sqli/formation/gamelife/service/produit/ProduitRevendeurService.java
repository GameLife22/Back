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
import java.util.List;
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
    public List<ProduitRevendeurReponse> recupererProduitRevendeursParProduit(UUID pProduitId) {
        LOGGER.info("Tentative de récupération de la liste des revendeurs vendant un produit avec l'identifiant : {}", pProduitId);
        List<ProduitRevendeurReponse> produitRevendeur = IProduitRevendeurConvertisseur.convertirEnProduitRevendeursReponse(this.produitRevendeurDao.findByProduitId(pProduitId)
                .orElseThrow(() -> new EntityNotFoundException("Aucun liste des revendeurs vendant un produit trouvé avec l'identifiant fourni : " + pProduitId)));
        LOGGER.info("Liste des revendeurs vendant un produit récupéré avec succès : {}", produitRevendeur);
        return produitRevendeur;
    }
}
