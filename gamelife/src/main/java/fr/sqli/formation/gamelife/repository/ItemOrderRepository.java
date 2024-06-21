package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ItemOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrderEntity, UUID> {
    //Optional<ItemOrderEntity> findByIdCommandeAndIdProduitRevendeur(UUID pItemCommandeId, UUID pProduitRevendeurId);

}

