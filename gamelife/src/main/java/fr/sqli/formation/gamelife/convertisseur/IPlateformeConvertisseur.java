package fr.sqli.formation.gamelife.convertisseur;

import fr.sqli.formation.gamelife.dto.platforme.PlateformeRequete;
import fr.sqli.formation.gamelife.dto.platforme.PlateformeReponse;
import fr.sqli.formation.gamelife.entite.PlateformeEntite;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public interface IPlateformeConvertisseur {
    public static PlateformeEntite toEntity(PlateformeRequete pPlateformeRequete) {
        var plateformeEntity = new PlateformeEntite();
        plateformeEntity.setLibelle(pPlateformeRequete.getLibelle());
        return plateformeEntity;
    }

    public static Set<PlateformeEntite> toEntities(Set<PlateformeRequete> pPlateformeRequete) {
        return pPlateformeRequete.stream()
                .map(IPlateformeConvertisseur::toEntity)
                .collect(Collectors.toSet());
    }

    public static PlateformeEntite entityFromDtoIn(PlateformeRequete pPlateformeRequete, PlateformeEntite pPlateformeEntite) {
        pPlateformeEntite.setLibelle(pPlateformeRequete.getLibelle());
        return pPlateformeEntite;
    }

    public static PlateformeReponse dtoOutFromEntity(PlateformeEntite pPlateformeEntite) {
        var plateformeDtoOut = new PlateformeReponse();
        plateformeDtoOut.setId(pPlateformeEntite.getId());
        plateformeDtoOut.setLibelle(pPlateformeEntite.getLibelle());
        return plateformeDtoOut;
    }

    public static Set<PlateformeReponse> dtoOutFromEntities(Set<PlateformeEntite> pPlateformeEntities) {
        return pPlateformeEntities.stream()
                .map(IPlateformeConvertisseur::dtoOutFromEntity)
                .collect(Collectors.toSet());
    }
}
