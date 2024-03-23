package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.PlateformeEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPlateformeDao extends IJpaDao<PlateformeEntity> {
    Optional<PlateformeEntity> findByLibelle(String pLibelle);
}
