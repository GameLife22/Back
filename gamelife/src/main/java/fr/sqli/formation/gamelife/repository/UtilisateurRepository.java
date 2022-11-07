package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Integer> {
//    @Query("FROM UtilisateurEntity  as c WHERE c.email=:unEmail")
//    public UtilisateurEntity trouverUtilisateur (@Param("unEmail")String unEmail);

    public UtilisateurEntity findUtilisateurEntityByEmail (String unEmail);

}
