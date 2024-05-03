package fr.sqli.formation.gamelife.dao;

import fr.sqli.formation.gamelife.entite.ProduitRevendeurEntite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface IProduitRevendeurDao extends JpaRepository<ProduitRevendeurEntite, UUID> {
    //Optional<ProduitRevendeurEntity> findByIdProduitAndIdUtilisateur(UUID produitId, UUID pUtilisateurId);
    Optional<List<ProduitRevendeurEntite>> findProduitByProduitId(UUID pProduitId);
}
