package fr.sqli.formation.gamelife.dto.resetMdp;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class resetMdpDtoHandler {


    public static UtilisateurEntity toEntity(resetMdpDtoIn dto) {

        var u = new UtilisateurEntity();
        u.setMdp(dto.getPwd());
        return u;
    }


}
