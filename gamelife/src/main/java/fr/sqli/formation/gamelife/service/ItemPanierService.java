package fr.sqli.formation.gamelife.service;


import fr.sqli.formation.gamelife.dto.itemPanier.ItemPanierDtoHandler;
import fr.sqli.formation.gamelife.dto.itemPanier.ItemPanierDtoIn;
import fr.sqli.formation.gamelife.dto.itemPanier.ItemPanierDtoOut;
import fr.sqli.formation.gamelife.entity.ItemPanierEntity;
import fr.sqli.formation.gamelife.entity.ItemPanierPK;
import fr.sqli.formation.gamelife.ex.ItemPanierExistantException;
import fr.sqli.formation.gamelife.ex.ItemPanierNonExistantException;
import fr.sqli.formation.gamelife.repository.ItemPanierRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPanierService {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private ItemPanierRepository uDao;
    ItemPanierPK id = new ItemPanierPK();

    public ItemPanierEntity creerItemPanier(ItemPanierDtoIn dto) throws Exception {


        id.setIdPanier(dto.getId_panier());
        id.setIdProduit(dto.getId_produit());

        ItemPanierEntity.validateAll(id, dto.getQuantite());

        var newItemPanier = uDao.findItemPanierEntitiesByPanierIdAndProduitId(dto.getId_panier(), dto.getId_produit());

        if (newItemPanier.get().size() == 0) {
            ItemPanierEntity p = ItemPanierDtoHandler.toEntity(dto);
            return uDao.save(p);
        } else {
            throw new ItemPanierExistantException("ItemPanier déjà existant");
        }
    }

    public void supprimerItemPanier(ItemPanierDtoIn dto) throws Exception {

        id.setIdPanier(dto.getId_panier());
        id.setIdProduit(dto.getId_produit());

        ItemPanierEntity.validateAll(id, dto.getQuantite());
        var ItemPanier = uDao.findItemPanierEntitiesByPanierIdAndProduitId(dto.getId_panier(), dto.getId_produit());
        if (ItemPanier.get().size() > 0) {
            ItemPanierEntity p = ItemPanierDtoHandler.toEntity(dto);
            uDao.delete(p);
        } else {
            throw new ItemPanierNonExistantException("ItemPanier Non Existant");
        }
    }

    public ItemPanierEntity modifierItemPanier(ItemPanierDtoIn dto) throws Exception {

        id.setIdPanier(dto.getId_panier());
        id.setIdProduit(dto.getId_produit());

        ItemPanierEntity.validateId(id);
        var ItemPanier = uDao.findItemPanierEntitiesByPanierIdAndProduitId(dto.getId_panier(), dto.getId_produit());
        if (ItemPanier.get().size() > 0) {
            ItemPanierEntity p = ItemPanierDtoHandler.toEntity(dto);
            return uDao.save(p);
        }
        throw new IllegalArgumentException();
    }

    public ItemPanierEntity getItemPanier(ItemPanierDtoOut dto) throws Exception {

        id.setIdPanier(dto.getId_panier());
        id.setIdProduit(dto.getId_produit());

        ItemPanierEntity.validateId(id);
        var ItemPanier = uDao.findItemPanierEntitiesByPanierIdAndProduitId(dto.getId_panier(), dto.getId_produit());
        if (ItemPanier.get().size() > 0) {
            LOG.debug("ItemPanier Trouvé");
            return  ItemPanier.get().get(0);
        }
        throw new ItemPanierNonExistantException();
    }
}