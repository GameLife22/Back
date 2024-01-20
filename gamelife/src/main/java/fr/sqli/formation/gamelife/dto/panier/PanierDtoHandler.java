package fr.sqli.formation.gamelife.dto.panier;

import fr.sqli.formation.gamelife.dto.ProduitDtoHandler;
import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDtoHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PanierDtoHandler {
        public PanierDto entityToDto(PanierEntity panierEntity) {
            PanierDto panierDto = new PanierDto();
            panierDto.setId(panierEntity.getId());
            panierDto.setDate(panierEntity.getDate());
            panierDto.setEtat(panierEntity.getEtat());
            panierDto.setUtilisateur(UtilisateurDtoHandler.fromEntity(panierEntity.getUtilisateur()));

            // Mettez à jour la conversion de ItemPanierEntity vers ItemPanierDto ici
            if (panierEntity.getItemPaniers() != null) {
                List<ItemPanierDto> itemPanierDtos = new ArrayList<>();
                for (ItemPanierEntity itemPanierEntity : panierEntity.getItemPaniers()) {
                    ItemPanierDto itemPanierDto = new ItemPanierDto();

                    // Ajoutez la conversion des propriétés de ItemPanierEntity vers ItemPanierDto ici
                    if (itemPanierEntity.getId() != null) {
                        itemPanierDto.setId(ItemPanierPKDtoHandler.fromEntity(itemPanierEntity.getId()));
                    }

                    itemPanierDto.setQuantite(itemPanierEntity.getQuantite());

                    if (itemPanierEntity.getProduit() != null) {
                        itemPanierDto.setProduit(ProduitDtoHandler.fromEntity(itemPanierEntity.getProduit()));
                    }

                    itemPanierDtos.add(itemPanierDto);
                }
                panierDto.setItemPaniers(itemPanierDtos);
            }

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
