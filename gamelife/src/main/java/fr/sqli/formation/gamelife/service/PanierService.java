package fr.sqli.formation.gamelife.service;


import fr.sqli.formation.gamelife.dto.panier.PanierDtoHandler;
import fr.sqli.formation.gamelife.dto.panier.PanierDtoIn;
import fr.sqli.formation.gamelife.entity.PanierEntity;
import fr.sqli.formation.gamelife.entity.PanierPK;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.ex.PanierExistantException;
import fr.sqli.formation.gamelife.ex.PanierNonExistantException;
import fr.sqli.formation.gamelife.repository.PanierRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PanierService {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private PanierRepository uDao;
    PanierPK id = new PanierPK();

    public PanierEntity creerPanier(PanierDtoIn dto) throws Exception {


        id.setIdCommande(dto.getId_commande());
        id.setIdProduit(dto.getId_produit());

        PanierEntity.validateAll(id, dto.getQuantite());

        var newPanier = uDao.findPanierEntitiesByCommandeIdAndProduitId(dto.getId_commande(), dto.getId_produit());

        if (newPanier.get().size() == 0) {
            PanierEntity p = PanierDtoHandler.toEntity(dto);
            return uDao.save(p);
        } else {
            throw new PanierExistantException("Panier déjà existant");
        }
    }

    public void supprimerPanier(PanierDtoIn dto) throws Exception {

        id.setIdCommande(dto.getId_commande());
        id.setIdProduit(dto.getId_produit());

        PanierEntity.validateAll(id, dto.getQuantite());
        var panier = uDao.findPanierEntitiesByCommandeIdAndProduitId(dto.getId_commande(), dto.getId_produit());
        if (panier.get().size() > 0) {
            PanierEntity p = PanierDtoHandler.toEntity(dto);
            uDao.delete(p);
        } else {
            throw new PanierNonExistantException("Panier Non Existant");
        }
    }

    public PanierEntity modifierPanier(PanierDtoIn dto) throws Exception {

        id.setIdCommande(dto.getId_commande());
        id.setIdProduit(dto.getId_produit());

        PanierEntity.validateId(id);
        var panier = uDao.findPanierEntitiesByCommandeIdAndProduitId(dto.getId_commande(), dto.getId_produit());
        if (panier.get().size() > 0) {
            PanierEntity p = PanierDtoHandler.toEntity(dto);
            return uDao.save(p);
        }
        throw new IllegalArgumentException();
    }

    public List<PanierEntity> getPanier(PanierDtoIn dto) throws Exception {

        id.setIdCommande(dto.getId_commande());
        id.setIdProduit(dto.getId_produit());

        PanierEntity.validateId(id);
        var panier = uDao.findPanierEntitiesByCommandeIdAndProduitId(dto.getId_commande(), dto.getId_produit());
        if (panier.get().size() > 0) {
            LOG.debug("Panier Trouvé");
            return panier.get();
        }
        throw new PanierNonExistantException();
    }
}