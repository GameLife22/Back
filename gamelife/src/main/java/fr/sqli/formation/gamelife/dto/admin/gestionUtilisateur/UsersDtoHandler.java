package fr.sqli.formation.gamelife.dto.admin.gestionUtilisateur;

import fr.sqli.formation.gamelife.dto.gestionCompte.UserDtoOut;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class UsersDtoHandler {
    public static UsersDtoOut fromEntity(UtilisateurEntity entity) {
        var u = new UsersDtoOut();
        u.setId(entity.getId());
        u.setPrenom(entity.getPrenom());
        u.setNom(entity.getNom());
        u.setEmail(entity.getEmail());
        u.setVille(entity.getVille());
        u.setRue(entity.getRue());
        u.setNum_rue(entity.getNum_rue());
        u.setNum_siren(entity.getNumSiret());
        u.setCode_postal(entity.getCodePostal());
        u.setEtat_compte(entity.getEtatCompte());
        u.setRole(entity.getRole());
        return u;
    }

    public static UtilisateurEntity fromDto(UsersDtoOut dto) {

        var u = new UtilisateurEntity();
        u.setId(dto.getId());
        u.setPrenom(dto.getPrenom());
        u.setNom(dto.getNom());
        u.setEmail(dto.getEmail());
        u.setVille(dto.getVille());
        u.setRue(dto.getRue());
        u.setNum_rue(dto.getNum_rue());
        u.setNumSiret(dto.getNum_siren());
        u.setCodePostal(dto.getCode_postal());
        u.setEtatCompte(dto.getEtat_compte());
        u.setRole(dto.getRole());
        return u;
    }
}
