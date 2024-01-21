package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ItemCommandeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCommandeRepository extends JpaRepository<ItemCommandeEntity, Integer> {

}

