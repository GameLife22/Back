package fr.sqli.formation.gamelife.repository;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

    @Repository
    public interface CommandeRepository extends JpaRepository<CommandeEntity, Integer> {
        @Query("SELECT c FROM CommandeEntity c JOIN FETCH c.itemsCommande WHERE c.id = :id")
        Optional<CommandeEntity> findByIdWithItemCommandes(@Param("id") int id);
    }

