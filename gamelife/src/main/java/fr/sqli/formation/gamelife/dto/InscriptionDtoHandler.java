package fr.sqli.formation.gamelife.dto;


import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class InscriptionDtoHandler {

	public static InscriptionDto fromEntity(InscriptionDto entity) {
		var u = new InscriptionDto();
		u.setPrenom(entity.getPrenom());
		u.setNom(entity.getNom());
		u.setMdp(entity.getMdp());
		u.setEmail(entity.getEmail());
		u.setVille(entity.getVille());
		u.setRue(entity.getRue());
		u.setNum_rue(entity.getNum_rue());
		u.setNum_siren(entity.getNum_siren());
		u.setEtat(entity.getEtat());
		u.setRole(entity.getRole());
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
		u.setNumSiren(dto.getNum_siren());
		u.setEtatCompte(dto.getEtat());
		u.setRole(dto.getRole());
		return u;
	}

}
