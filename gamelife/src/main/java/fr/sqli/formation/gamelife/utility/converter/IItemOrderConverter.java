package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.request.ItemOrderRequest;
import fr.sqli.formation.gamelife.dto.response.ItemOrderResponse;
import fr.sqli.formation.gamelife.entity.OrderEntity;
import fr.sqli.formation.gamelife.entity.ItemOrderEntity;
import fr.sqli.formation.gamelife.entity.SellerGameEntity;

public interface IItemOrderConverter {

    public static ItemOrderEntity DtoToEntity(ItemOrderRequest dto) {
        ItemOrderEntity itemOrderEntity = new ItemOrderEntity();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(dto.getIdCommande());
        itemOrderEntity.setCommande(orderEntity);

        SellerGameEntity sellerGameEntity = new SellerGameEntity();
        sellerGameEntity.setId(dto.getIdProduitRevendeur());
        itemOrderEntity.setProduitRevendeur(sellerGameEntity);

        itemOrderEntity.setQuantite(dto.getQuantite());

        return itemOrderEntity;
    }


    public static ItemOrderResponse EntityToDto(ItemOrderEntity entity) {
        ItemOrderResponse itemOrderResponse = new ItemOrderResponse();
        itemOrderResponse.setId(entity.getId());
        itemOrderResponse.setIdCommande(entity.getCommande().getId());
        itemOrderResponse.setIdProduitRevendeur(entity.recupererProduitRevendeur().getId());
        itemOrderResponse.setQuantite(entity.getQuantite());
        return itemOrderResponse;
    }
}
