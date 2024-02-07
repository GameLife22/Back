package fr.sqli.formation.gamelife.dto.inscription;


import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class InscriptionDtoHandler {

	public static InscriptionDto fromEntity(UtilisateurEntity entity) {
		var u = new InscriptionDto();
		u.setPrenom(entity.getPrenom());
		u.setNom(entity.getNom());
		u.setMdp(entity.getMdp());
		u.setEmail(entity.getEmail());
		u.setVille(entity.getVille());
		u.setRue(entity.getRue());
		u.setNum_rue(entity.getNumRue());
		u.setNum_siret(entity.getNumSiren());
		u.setCode_postal(entity.getCodePostal());
		return u;
	}

	public static UtilisateurEntity fromDto(InscriptionDto dto) {

		var u = new UtilisateurEntity();
		u.setPrenom(dto.getPrenom());
		u.setNom(dto.getNom());
		u.setMdp(dto.getMdp());
		u.setEmail(dto.getEmail());
		u.setVille(dto.getVille());
		u.setRue(dto.getRue());
		u.setNumRue(dto.getNum_rue());
		u.setNumSiren(dto.getNum_siret());
		u.setCodePostal(dto.getCode_postal());
		return u;
	}

}
