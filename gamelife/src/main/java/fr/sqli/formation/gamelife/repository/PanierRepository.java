package fr.sqli.formation.gamelife.repository;
import fr.sqli.formation.gamelife.dto.panier.PanierDto;
import fr.sqli.formation.gamelife.entity.PanierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PanierRepository extends JpaRepository<PanierEntity,Integer> {
    @Query("SELECT p FROM PanierEntity p LEFT JOIN FETCH p.itemPaniers WHERE p.id = :id")
    Optional<PanierEntity> findByIdWithItemPaniers(@Param("id") int id);

}
