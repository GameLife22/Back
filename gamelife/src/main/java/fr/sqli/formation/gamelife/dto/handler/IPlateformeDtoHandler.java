package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.PlateformeDtoIn;
import fr.sqli.formation.gamelife.dto.out.PlateformeDtoOut;
import fr.sqli.formation.gamelife.entity.PlateformeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface IPlateformeDtoHandler {
    public static PlateformeEntity toEntity(PlateformeDtoIn pPlateformeDtoIn) {
        var plateformeEntity = new PlateformeEntity();
        plateformeEntity.setLibelle(pPlateformeDtoIn.getLibelle());
        return plateformeEntity;
    }

    public static PlateformeEntity entityFromDtoIn(PlateformeDtoIn pPlateformeDtoIn, PlateformeEntity plateformeEntity) {
        plateformeEntity.setLibelle(pPlateformeDtoIn.getLibelle());
        return plateformeEntity;
    }

    public static PlateformeDtoOut dtoOutFromEntity(PlateformeEntity pPlateformeEntity) {
        var plateformeDtoOut = new PlateformeDtoOut();
        plateformeDtoOut.setId(pPlateformeEntity.getId());
        plateformeDtoOut.setLibelle(pPlateformeEntity.getLibelle());
        return plateformeDtoOut;
    }

    public static List<PlateformeDtoOut> dtoOutFromEntities(List<PlateformeEntity> pPlateformeEntities) {
        return pPlateformeEntities.stream()
                .map(IPlateformeDtoHandler::dtoOutFromEntity)
                .collect(Collectors.toList());
    }
}
