package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.request.LoginRequest;
import fr.sqli.formation.gamelife.dto.response.LoginResponse;
import fr.sqli.formation.gamelife.entity.UserEntity;

public interface ILoginConverter {

    public static LoginResponse fromEntity(UserEntity entity) {
        var u = new LoginResponse();
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

    public static UserEntity toEntity(LoginRequest dto) {

        var u = new UserEntity();
        u.setEmail(dto.email());
        u.setMdp(dto.password());
        return u;
    }
}
