package fr.sqli.formation.gamelife.dto.panier;

import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDto;
import fr.sqli.formation.gamelife.entity.PanierEntity;

public class PanierDtoHandler {
    public PanierDto entityToDto(PanierEntity panierEntity) {
        PanierDto panierDto = new PanierDto();
        panierDto.setId(panierEntity.getId());
        panierDto.setDate(panierEntity.getDate());
        panierDto.setEtat(panierEntity.getEtat());
        panierDto.setUtilisateur(new UtilisateurDto());
        return panierDto;
    }

    public PanierEntity dtoToEntity(PanierDto panierDto) {
        PanierEntity panierEntity = new PanierEntity();
        panierEntity.setId(panierDto.getId());
        panierEntity.setDate(panierDto.getDate());
        panierEntity.setEtat(panierDto.getEtat());
        return panierEntity;
    }
}
