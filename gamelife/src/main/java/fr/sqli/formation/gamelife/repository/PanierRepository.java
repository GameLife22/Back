package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.PanierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<PanierEntity,Integer> {


}
