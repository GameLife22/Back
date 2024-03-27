package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ImageDtoIn;
import fr.sqli.formation.gamelife.dto.out.ImageDtoOut;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface IImageDtoHandler {
    public static ImageEntity toEntity(ImageDtoIn pImagesDtoIn) {
        var imageEntity = new ImageEntity();
        imageEntity.setImage(pImagesDtoIn.getImage());
        imageEntity.setTitre(pImagesDtoIn.getTitre());
        return imageEntity;
    }

    public static List<ImageEntity> toEntities(List<ImageDtoIn> pImagesDtoIn) {
    return pImagesDtoIn.stream()
            .map(IImageDtoHandler::toEntity)
            .collect(Collectors.toList());
    }

    public static ImageDtoOut dtoOutFromEntity(ImageEntity pImageEntitie) {
        var imageDtoOut = new ImageDtoOut();
        imageDtoOut.setId(pImageEntitie.getId());
        imageDtoOut.setImage(pImageEntitie.getImage());
        imageDtoOut.setTitre(pImageEntitie.getTitre());
        return imageDtoOut;
    }

    public static List<ImageDtoOut> dtoOutFromEntities(List<ImageEntity> pImageEntities) {
        return pImageEntities.stream()
                .map(imageEntity -> dtoOutFromEntity(imageEntity))
                .collect(Collectors.toList());
    }
}
