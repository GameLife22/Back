package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ImageDtoIn;
import fr.sqli.formation.gamelife.dto.out.ImageDtoOut;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface IImageDtoHandler {
    public static ImageEntity toEntity(ImageDtoIn pImagesDtoIn) {
        return null;
    }

    public static List<ImageEntity> toEntities(List<ImageDtoIn> pImagesDtoIn) {
    return pImagesDtoIn.stream()
            .map(IImageDtoHandler::toEntity)
            .collect(Collectors.toList());
    }

    public static ImageDtoOut dtoOutFromEntity(ProduitEntity pProduitEntity, ImageEntity pImageEntitie) {
        return null;
    }

    public static List<ImageDtoOut> dtoOutFromEntities(ProduitEntity pProduitEntity, List<ImageEntity> pImageEntities) {
        return pImageEntities.stream()
                .map(imageEntity -> dtoOutFromEntity(pProduitEntity, imageEntity))
                .collect(Collectors.toList());
    }
}
