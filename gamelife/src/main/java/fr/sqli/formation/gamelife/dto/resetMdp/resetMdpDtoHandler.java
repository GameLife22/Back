package fr.sqli.formation.gamelife.dto.resetMdp;

import fr.sqli.formation.gamelife.entite.UtilisateurEntite;

public class resetMdpDtoHandler {


    public static UtilisateurEntite toEntity(resetMdpDtoIn dto) {

        var u = new UtilisateurEntite();
        u.setMdp(dto.getPwd());
        return u;
    }


}
