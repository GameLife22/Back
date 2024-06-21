package fr.sqli.formation.gamelife.convertisseur;

import fr.sqli.formation.gamelife.dto.commande.CommandeRequete;
import fr.sqli.formation.gamelife.dto.commande.CommandeReponse;
import fr.sqli.formation.gamelife.entite.CommandeEntite;
import fr.sqli.formation.gamelife.entite.ItemCommandeEntite;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ICommandeConvertisseur {

    public static CommandeEntite DtoToEntity(CommandeRequete dto) {
        CommandeEntite CommandeEntite = new CommandeEntite();
        CommandeEntite.setId(dto.getId());
        CommandeEntite.setUtilisateur(new UtilisateurEntite(dto.getIdUtilisateur()));
        CommandeEntite.setEtat(dto.getEtat());
        CommandeEntite.setNumRueLivraison(dto.getNumRueLivraison());
        CommandeEntite.setRueLivraison(dto.getRueLivraison());
        CommandeEntite.setVilleLivraison(dto.getVilleLivraison());
        CommandeEntite.setCodePostalLivraison(dto.getCodePostalLivraison());
        CommandeEntite.setDate(dto.getDate());

        if (dto.getItemsCommande() != null) {
            List<ItemCommandeEntite> itemCommandeEntites = dto.getItemsCommande().stream()
                    .map(ItemCommandeConvertisseur::DtoToEntity)
                    .collect(Collectors.toList());
            CommandeEntite.setItemsCommande(itemCommandeEntites);
        }
        return CommandeEntite;
    }


    public static CommandeReponse EntityToDto(CommandeEntite entity) {
        CommandeReponse commandeReponse = new CommandeReponse();
        commandeReponse.setId(entity.getId());
        commandeReponse.setEtat(entity.getEtat());
        commandeReponse.setNumRueLivraison(entity.getNumRueLivraison());
        commandeReponse.setRueLivraison(entity.getRueLivraison());
        commandeReponse.setVilleLivraison(entity.getVilleLivraison());
        commandeReponse.setCodePostalLivraison(entity.getCodePostalLivraison());
        commandeReponse.setDate(entity.getDate());

        if (entity.getItemsCommande() != null) {
            List<UUID> itemIds = entity.getItemsCommande().stream()
                    .map(ItemCommandeEntite::getId)
                    .collect(Collectors.toList());
        }


        return commandeReponse;
    }
}
