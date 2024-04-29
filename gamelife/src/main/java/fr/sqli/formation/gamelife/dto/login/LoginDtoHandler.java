package fr.sqli.formation.gamelife.dto.login;

import fr.sqli.formation.gamelife.entite.UtilisateurEntite;

public class LoginDtoHandler {

    public static LoginDtoOut fromEntity(UtilisateurEntite entity) {
        var u = new LoginDtoOut();
        //todo: replace
        //u.setId(entity.getId());
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

    public static UtilisateurEntite toEntity(LoginDtoIn dto) {

        var u = new UtilisateurEntite();
        u.setEmail(dto.getLogin());
        u.setMdp(dto.getPwd());
        return u;
    }


}
