package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.out.CommandeDtoOut;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.entity.ItemCommandeEntity;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CommandeDtoHandler {

    public static CommandeEntity DtoToEntity(CommandeDtoIn dto) {
        CommandeEntity entity = new CommandeEntity();
        // Mapper les propriétés de dto vers entity
        entity.setIdUtilisateur(new UtilisateurEntity(dto.getIdUtilisateur()));
        entity.setEtat(dto.getEtat());
        entity.setNumRueLivraison(dto.getNumRueLivraison());
        entity.setRueLivraison(dto.getRueLivraison());
        entity.setVilleLivraison(dto.getVilleLivraison());
        entity.setCodePostalLivraison(dto.getCodePostalLivraison());
        entity.setDate(dto.getDate());

        if (dto.getIdItemsCommande() != null) {
            List<ItemCommandeEntity> items = dto.getIdItemsCommande().stream()
                    .map(itemId -> new ItemCommandeEntity(itemId))
                    .collect(Collectors.toList());
            entity.setItems(items);
        }

        return entity;
    }

    public static CommandeDtoOut EntityToDto(CommandeEntity entity) {
        CommandeDtoOut dto = new CommandeDtoOut();
        // Mapper les propriétés de entity vers dto
        dto.setEtat(entity.getEtat());
        dto.setNumRueLivraison(entity.getNumRueLivraison());
        dto.setRueLivraison(entity.getRueLivraison());
        dto.setVilleLivraison(entity.getVilleLivraison());
        dto.setCodePostalLivraison(entity.getCodePostalLivraison());
        dto.setDate(entity.getDate());

        if (entity.getItems() != null) {
            List<Integer> itemIds = entity.getItems().stream()
                    .map(ItemCommandeEntity::getId)
                    .collect(Collectors.toList());
            dto.setIdItemsCommande(itemIds);
        }

        return dto;
    }

}
