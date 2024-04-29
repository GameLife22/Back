package fr.sqli.formation.gamelife.service.plateforme;

import fr.sqli.formation.gamelife.dto.platforme.PlateformeRequete;
import fr.sqli.formation.gamelife.dto.platforme.PlateformeReponse;

import java.util.Set;
import java.util.UUID;

public interface IPlateformeService {
    public PlateformeReponse getPlateforme(UUID pIdPlateforme);
    public Set<PlateformeReponse> getPlateformes();
    public PlateformeReponse creerPlateforme(PlateformeRequete pPlateformeRequete);
    public PlateformeReponse modifierPlateforme(UUID pPlateformeDtoInId, PlateformeRequete pPlateformeRequete);
    public void deletePlateforme(UUID pIdPlateforme);
}
