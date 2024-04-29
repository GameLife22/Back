package fr.sqli.formation.gamelife.dao;

import fr.sqli.formation.gamelife.entite.PlateformeEntite;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPlateformeDao extends IJpaDao<PlateformeEntite> {
    Optional<PlateformeEntite> findByLibelle(String pLibelle);
}
