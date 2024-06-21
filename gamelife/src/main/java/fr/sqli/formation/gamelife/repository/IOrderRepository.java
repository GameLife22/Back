package fr.sqli.formation.gamelife.repository;
import fr.sqli.formation.gamelife.entity.OrderEntity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@ComponentScan
public interface IOrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query("SELECT c FROM OrderEntity c JOIN FETCH c.itemsCommande WHERE c.id = :id")

     Optional<OrderEntity> findByIdWithItemCommandes(@Param("id") UUID id);

    //recuperer tous les produits qu'un utilisateur a dans son panier
     @Query("SELECT c FROM OrderEntity c JOIN FETCH c.itemsCommande WHERE c.user.id = :id")
      Optional<OrderEntity> findByUtilisateurIdWithItemCommandes(@Param("id") UUID id);

     Optional<OrderEntity> findByUtilisateurId(UUID pIdUtilisateur);
}