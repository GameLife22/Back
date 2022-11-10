package fr.sqli.formation.gamelife.dto;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class LoginDtoHandler {

    public static LoginDto fromEntity(UtilisateurEntity entity) {
        var u = new LoginDto();
        u.setLogin(entity.getEmail());
        u.setPwd(entity.getMdp());
        return u;
    }

    public static UtilisateurEntity fromDto(LoginDto dto) {

        var u = new UtilisateurEntity();
        u.setEmail(dto.getLogin());
        u.setMdp(dto.getPwd());
        return u;
    }


}
