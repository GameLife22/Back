package fr.sqli.formation.gamelife.dto.mdpOublie;

import fr.sqli.formation.gamelife.dto.login.LoginDtoOut;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class MdpOublieDtoHandler {


    public static UtilisateurEntity toEntity(MdpOublieDtoIn dto) {

        var u = new UtilisateurEntity();
        u.setEmail(dto.getLogin());
        return u;
    }


}
