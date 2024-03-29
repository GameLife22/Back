package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.entity.ProduitRevendeurEntity;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProduitRevendeurRepository extends JpaRepository<ProduitRevendeurEntity, Integer> {
    Optional<ProduitRevendeurEntity> findByIdProduitAndIdUtilisateur(ProduitEntity produitEntity, UtilisateurEntity idUtilisateur);
}
