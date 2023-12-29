package fr.sqli.formation.gamelife.dto.mdpOublie;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class MdpOublieDtoHandler {


    public static UtilisateurEntity toEntity(MdpOublieDtoIn dto) {

        var u = new UtilisateurEntity();
        u.setEmail(dto.getLogin());
        return u;
    }

    public static EmailDtoOut fromEntity(UtilisateurEntity entity) {
        var u = new EmailDtoOut();
        u.setLogin(entity.getEmail());
        return u;
    }



}
