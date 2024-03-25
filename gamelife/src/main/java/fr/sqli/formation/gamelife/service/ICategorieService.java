package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.in.CategorieDtoIn;
import fr.sqli.formation.gamelife.dto.out.CategorieDtoOut;

import java.util.List;
import java.util.UUID;

public interface ICategorieService {
    public CategorieDtoOut getCategorie(UUID pCategorieId);
    public List<CategorieDtoOut> getCategories();
    public CategorieDtoOut addCategorie(CategorieDtoIn pCategorieDtoIn);
    public CategorieDtoOut updateCategorie(UUID pCategorieId, CategorieDtoIn pCategorieDtoIn);
    public void deleteCategorie(UUID pCategorieId);
}
