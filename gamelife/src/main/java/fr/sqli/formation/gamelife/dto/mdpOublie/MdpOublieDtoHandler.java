package fr.sqli.formation.gamelife.dto.mdpOublie;

import fr.sqli.formation.gamelife.entite.UtilisateurEntite;

public class MdpOublieDtoHandler {


    public static UtilisateurEntite toEntity(MdpOublieDtoIn dto) {

        var u = new UtilisateurEntite();
        u.setEmail(dto.getLogin());
        return u;
    }

    public static EmailDtoOut fromEntity(UtilisateurEntite entity) {
        var u = new EmailDtoOut();
        u.setLogin(entity.getEmail());
        return u;
    }



}
