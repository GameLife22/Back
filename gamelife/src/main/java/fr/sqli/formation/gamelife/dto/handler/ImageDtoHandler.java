package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ImageDtoIn;
import fr.sqli.formation.gamelife.dto.out.ImageDtoOut;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public interface ImageDtoHandler {
    public static ImageEntity toEntity(ImageDtoIn pImagesDtoIn) {
        var imageEntity = new ImageEntity();
        var produitEntity = new ProduitEntity();

        produitEntity.setId(pImagesDtoIn.getIdProduit());

        imageEntity.setImage(pImagesDtoIn.getImage());
        imageEntity.setTitre(pImagesDtoIn.getTitre());
        imageEntity.setProduit(produitEntity);

        return imageEntity;
    }

    public static List<ImageEntity> toEntities(List<ImageDtoIn> pImagesDtoIn) {
    return pImagesDtoIn.stream()
            .filter(Objects::nonNull)
            .map(ImageDtoHandler::toEntity)
            .collect(Collectors.toList());
    }

    public static ImageDtoOut dtoOutFromEntity(ProduitEntity pProduitEntity, ImageEntity pImageEntitie) {
        var imageDtoOut = new ImageDtoOut();
        imageDtoOut.setId(pImageEntitie.getId());
        imageDtoOut.setImage(pImageEntitie.getImage());
        imageDtoOut.setTitre(pImageEntitie.getTitre());
        imageDtoOut.setIdProduit(pProduitEntity.getId());
        return imageDtoOut;
    }

    public static List<ImageDtoOut> dtoOutFromEntities(ProduitEntity pProduitEntity, List<ImageEntity> pImageEntities) {
        return pImageEntities.stream()
                .map(imageEntity -> dtoOutFromEntity(pProduitEntity, imageEntity))
                .collect(Collectors.toList());
    }
}
