package fr.sqli.formation.gamelife.dao;
import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurRequete;
import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDto;
import fr.sqli.formation.gamelife.entite.CommandeEntite;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
    @ComponentScan
    public interface ICommandeDao extends JpaRepository<CommandeEntite, UUID> {
        @Query("SELECT c FROM CommandeEntite c JOIN FETCH c.itemsCommande WHERE c.id = :id")
        Optional<CommandeEntite> findByIdWithItemCommandes(@Param("id") UUID id);

        Optional<CommandeEntite> findByUtilisateurId(UUID pIdUtilisateur);
}