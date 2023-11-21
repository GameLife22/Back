package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ItemPanierEntity;
import fr.sqli.formation.gamelife.entity.ItemPanierPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemPanierRepository extends JpaRepository<ItemPanierEntity, ItemPanierPK> {

}

