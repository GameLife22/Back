package fr.sqli.formation.gamelife.dao;

import fr.sqli.formation.gamelife.entite.ItemCommandeEntite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ItemCommandeDao extends JpaRepository<ItemCommandeEntite, UUID> {
    //Optional<ItemCommandeEntity> findByIdCommandeAndIdProduitRevendeur(UUID pItemCommandeId, UUID pProduitRevendeurId);
}

