package fr.sqli.formation.gamelife.dao;

import fr.sqli.formation.gamelife.entite.CommandeEntite;
import fr.sqli.formation.gamelife.entite.ProduitRevendeurEntite;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface IProduitRevendeurDao extends JpaRepository<ProduitRevendeurEntite, UUID> {
    public Optional<ProduitRevendeurEntite> findById(UUID pUUID);
    @Override
    List<ProduitRevendeurEntite> findAll();
}
