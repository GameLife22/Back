package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.entity.ItemCommandeEntity;
import fr.sqli.formation.gamelife.entity.ProduitRevendeurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemCommandeRepository extends JpaRepository<ItemCommandeEntity, Integer> {
    Optional<ItemCommandeEntity> findByIdCommandeAndIdProduitRevendeur(CommandeEntity commande, ProduitRevendeurEntity produitRevendeur);
}

