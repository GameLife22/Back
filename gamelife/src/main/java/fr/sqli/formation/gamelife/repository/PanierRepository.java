package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.entity.PanierEntity;
import fr.sqli.formation.gamelife.entity.PanierPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PanierRepository extends JpaRepository<PanierEntity, PanierPK> {

    /**
     * Cette fonction cherche des paniers dans la base de données en utilisant les parametres fournit
     * @param id_Commande
     * @param id_Produit
     * @return
     */
    public Optional<List<PanierEntity>> findPanierEntitiesByCommandeIdAndProduitId(Integer id_Commande,Integer id_Produit);

}
