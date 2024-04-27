package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.entity.OrderEntity;
import fr.sqli.formation.gamelife.entity.ItemOrderEntity;
import fr.sqli.formation.gamelife.entity.UserEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface IOrderConverter {

    public static OrderEntity DtoToEntity(OrderRequest dto) {
        OrderEntity OrderEntity = new OrderEntity();
        OrderEntity.setUtilisateur(new UserEntity(dto.getIdUtilisateur()));
        OrderEntity.setEtat(dto.getEtat());
        OrderEntity.setNumRueLivraison(dto.getNumRueLivraison());
        OrderEntity.setRueLivraison(dto.getRueLivraison());
        OrderEntity.setVilleLivraison(dto.getVilleLivraison());
        OrderEntity.setCodePostalLivraison(dto.getCodePostalLivraison());
        OrderEntity.setDate(dto.getDate());
        return OrderEntity;
    }


    public static OrderResponse EntityToDto(OrderEntity entity) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setEtat(entity.getEtat());
        orderResponse.setNumRueLivraison(entity.getNumRueLivraison());
        orderResponse.setRueLivraison(entity.getRueLivraison());
        orderResponse.setVilleLivraison(entity.getVilleLivraison());
        orderResponse.setCodePostalLivraison(entity.getCodePostalLivraison());
        orderResponse.setDate(entity.getDate());

        if (entity.getItemsCommande() != null) {
            List<UUID> itemIds = entity.getItemsCommande().stream()
                    .map(ItemOrderEntity::getId)
                    .toList();
        }

        return orderResponse;
    }
}