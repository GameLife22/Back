package fr.sqli.formation.gamelife.repository;


import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity,Integer> {

    // @Query("FROM UtilisateurEntity as c WHERE c.email=:email")
    // public UtilisateurEntity trouverEmail(@Param("email") String email);
    public Optional<UtilisateurEntity> findByEmail(String email);
    //public Optional<UtilisateurEntity> findById(Integer id);


}
