package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.entity.OrderEntity;
import fr.sqli.formation.gamelife.entity.OrderEntity;
import fr.sqli.formation.gamelife.entity.ItemOrderEntity;
import fr.sqli.formation.gamelife.entity.UserEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface IOrderConverter {

    public static OrderEntity DtoToEntity(OrderRequest dto) {
        OrderEntity OrderEntity = new OrderEntity();
        OrderEntity.setId(dto.getId());
        OrderEntity.setUtilisateur(new UserEntity(dto.getIdUtilisateur()));
        OrderEntity.setEtat(dto.getEtat());
        OrderEntity.setStreetNumberLivraison(dto.getNumRueLivraison());
        OrderEntity.setStreetLivraison(dto.getRueLivraison());
        OrderEntity.setCityLivraison(dto.getVilleLivraison());
        OrderEntity.setZipCodeLivraison(dto.getCodePostalLivraison());
        OrderEntity.setDate(dto.getDate());

        if (dto.getItemsCommande() != null) {
            List<ItemOrderEntity> ItemOrderEntitys = dto.getItemsCommande().stream()
                    .map(IItemOrderConverter::DtoToEntity)
                    .collect(Collectors.toList());
            OrderEntity.setItemsCommande(ItemOrderEntitys);
        }
        return OrderEntity;
    }


    public static OrderResponse EntityToDto(OrderEntity entity) {
        OrderResponse OrderResponse = new OrderResponse();
        OrderResponse.setId(entity.getId());
        OrderResponse.setEtat(entity.getEtat());
        OrderResponse.setNumRueLivraison(entity.getStreetNumberLivraison());
        OrderResponse.setRueLivraison(entity.getStreetLivraison());
        OrderResponse.setVilleLivraison(entity.getCityLivraison());
        OrderResponse.setCodePostalLivraison(entity.getZipCodeLivraison());
        OrderResponse.setDate(entity.getDate());

        if (entity.getItemsCommande() != null) {
            List<UUID> itemIds = entity.getItemsCommande().stream()
                    .map(ItemOrderEntity::getId)
                    .collect(Collectors.toList());
        }


        return OrderResponse;
    }
}