package fr.sqli.formation.gamelife.repository;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommandeRepository extends JpaRepository<CommandeEntity, UUID> {

}
