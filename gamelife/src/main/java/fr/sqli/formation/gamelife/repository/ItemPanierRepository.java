package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ItemPanierEntity;
import fr.sqli.formation.gamelife.entity.ItemPanierPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemPanierRepository extends JpaRepository<ItemPanierEntity, ItemPanierPK> {

    /**
     * Cette fonction cherche des ItemPaniers dans la base de données en utilisant les parametres fournit
     * @param id_Panier
     * @param id_Produit
     * @return
     */
    public Optional<List<ItemPanierEntity>> findItemPanierEntitiesByPanierIdAndProduitId(Integer id_Panier,Integer id_Produit);

}
