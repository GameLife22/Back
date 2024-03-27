package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.in.PlateformeDtoIn;
import fr.sqli.formation.gamelife.dto.out.PlateformeDtoOut;

import java.util.List;
import java.util.UUID;

public interface IPlateformeService {
    public PlateformeDtoOut getPlateforme(UUID pIdPlateforme);
    public List<PlateformeDtoOut> getPlateformes();
    public PlateformeDtoOut addPlateforme(PlateformeDtoIn pPlateformeDtoIn);
    public PlateformeDtoOut updatePlateforme(UUID pPlateformeDtoInId, PlateformeDtoIn pPlateformeDtoIn);
    public void deletePlateforme(UUID pIdPlateforme);
}
