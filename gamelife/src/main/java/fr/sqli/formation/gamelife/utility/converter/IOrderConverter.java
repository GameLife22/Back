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
        OrderEntity.setStreetNumberLivraison(dto.getStreetNumberLivraison());
        OrderEntity.setStreetLivraison(dto.getStreetLivraison());
        OrderEntity.setCityLivraison(dto.getCityLivraison());
        OrderEntity.setZipCodeLivraison(dto.getZipCodeLivraison());
        OrderEntity.setDate(dto.getDate());
        return OrderEntity;
    }


    public static OrderResponse EntityToDto(OrderEntity entity) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setEtat(entity.getEtat());
        orderResponse.setStreetNumberLivraison(entity.getStreetNumberLivraison());
        orderResponse.setStreetLivraison(entity.getStreetLivraison());
        orderResponse.setCityLivraison(entity.getCityLivraison());
        orderResponse.setZipCodeLivraison(entity.getZipCodeLivraison());
        orderResponse.setDate(entity.getDate());

        if (entity.getItemsCommande() != null) {
            List<UUID> itemIds = entity.getItemsCommande().stream()
                    .map(ItemOrderEntity::getId)
                    .toList();
        }

        return orderResponse;
    }
}