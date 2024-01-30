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
        CommandeEntity CommandeEntity = new CommandeEntity();
        // Mapper les propriétés de dto vers entity
         CommandeEntity.setIdUtilisateur(new UtilisateurEntity(dto.getIdUtilisateur()));
         CommandeEntity.setEtat(dto.getEtat());
         CommandeEntity.setNumRueLivraison(dto.getNumRueLivraison());
         CommandeEntity.setRueLivraison(dto.getRueLivraison());
         CommandeEntity.setVilleLivraison(dto.getVilleLivraison());
         CommandeEntity.setCodePostalLivraison(dto.getCodePostalLivraison());
         CommandeEntity.setDate(dto.getDate());

        if (dto.getIdItemsCommande() != null) {
            List<ItemCommandeEntity> items = dto.getIdItemsCommande().stream()
                    .map(itemId -> new ItemCommandeEntity(itemId))
                    .collect(Collectors.toList());
            CommandeEntity.setItems(items);
        }

        return CommandeEntity;
    }

    public static CommandeDtoOut EntityToDto(CommandeEntity entity) {
        CommandeDtoOut CommandeDtoOut = new CommandeDtoOut();
        // Mapper les propriétés de entity vers dto
        CommandeDtoOut.setEtat(entity.getEtat());
        CommandeDtoOut.setNumRueLivraison(entity.getNumRueLivraison());
        CommandeDtoOut.setRueLivraison(entity.getRueLivraison());
        CommandeDtoOut.setVilleLivraison(entity.getVilleLivraison());
        CommandeDtoOut.setCodePostalLivraison(entity.getCodePostalLivraison());
        CommandeDtoOut.setDate(entity.getDate());

        if (entity.getItems() != null) {
            List<Integer> itemIds = entity.getItems().stream()
                    .map(ItemCommandeEntity::getId)
                    .collect(Collectors.toList());
            CommandeDtoOut.setIdItemsCommande(itemIds);
        }

        return CommandeDtoOut;
    }

}
