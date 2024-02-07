package fr.sqli.formation.gamelife.dto.login;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class LoginDtoHandler {

    public static LoginDtoOut fromEntity(UtilisateurEntity entity) {
        var u = new LoginDtoOut();
        u.setId(entity.getId());
        u.setEmail(entity.getEmail());
        u.setNom(entity.getNom());
        u.setEtat(entity.getEtatCompte());
        u.setNum_rue(entity.getNumRue());
        u.setRue(entity.getRue());
        u.setNum_siren(entity.getNumSiren());
        u.setPrenom(entity.getPrenom());
        u.setVille(entity.getVille());
        u.setCode_postal(entity.getCodePostal());
        return u;
    }

    public static UtilisateurEntity toEntity(LoginDtoIn dto) {

        var u = new UtilisateurEntity();
        u.setEmail(dto.getLogin());
        u.setMdp(dto.getPwd());
        return u;
    }


}
