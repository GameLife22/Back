package fr.sqli.formation.gamelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPanierRepository extends JpaRepository<ItemPanierEntity, ItemPanierPK> {

}

