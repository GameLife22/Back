package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.out.CommandeDtoOut;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.entity.ItemCommandeEntity;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandeDtoHandler {

    public static CommandeEntity DtoToEntity(CommandeDtoIn dto) {
        CommandeEntity CommandeEntity = new CommandeEntity();
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
            CommandeEntity.setItemsCommande(items);
        }

        return CommandeEntity;
    }


    public static CommandeDtoOut EntityToDto(CommandeEntity entity) {
        CommandeDtoOut commandeDtoOut = new CommandeDtoOut();
        commandeDtoOut.setEtat(entity.getEtat());
        commandeDtoOut.setNumRueLivraison(entity.getNumRueLivraison());
        commandeDtoOut.setRueLivraison(entity.getRueLivraison());
        commandeDtoOut.setVilleLivraison(entity.getVilleLivraison());
        commandeDtoOut.setCodePostalLivraison(entity.getCodePostalLivraison());
        commandeDtoOut.setDate(entity.getDate());

        if (entity.getItemsCommande() != null) {
            List<Integer> itemIds = entity.getItemsCommande().stream()
                    .map(ItemCommandeEntity::getId)
                    .collect(Collectors.toList());
            commandeDtoOut.setIdItemsCommande(itemIds);
        }

        return commandeDtoOut;
    }


}
