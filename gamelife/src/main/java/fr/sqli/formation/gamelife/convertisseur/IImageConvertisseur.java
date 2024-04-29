package fr.sqli.formation.gamelife.convertisseur;

import fr.sqli.formation.gamelife.dto.image.ImageRequete;
import fr.sqli.formation.gamelife.dto.image.ImageReponse;
import fr.sqli.formation.gamelife.entite.ImageEntite;
import fr.sqli.formation.gamelife.entite.ProduitEntite;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface IImageConvertisseur {
    public static ImageEntite toEntity(ImageRequete pImagesDtoIn, ProduitEntite pProduitEntite) {
        var imageEntity = new ImageEntite();
        imageEntity.setImage(pImagesDtoIn.getImage());
        imageEntity.setTitre(pImagesDtoIn.getTitre());
        imageEntity.setProduit(pProduitEntite);
        return imageEntity;
    }

    public static List<ImageEntite> toEntities(List<ImageRequete> pImagesDtoIn, ProduitEntite pProduitEntite) {
        return pImagesDtoIn.stream()
                .map(dto -> toEntity(dto, pProduitEntite))
                .collect(Collectors.toList());
    }

    public static ImageReponse dtoOutFromEntity(ImageEntite pImageEntite) {
        var imageDtoOut = new ImageReponse();
        imageDtoOut.setId(pImageEntite.getId());
        imageDtoOut.setImage(pImageEntite.getImage());
        imageDtoOut.setTitre(pImageEntite.getTitre());
        return imageDtoOut;
    }

    public static List<ImageReponse> dtoOutFromEntities(List<ImageEntite> pImageEntities) {
        return pImageEntities.stream()
                .map(IImageConvertisseur::dtoOutFromEntity)
                .collect(Collectors.toList());
    }
}