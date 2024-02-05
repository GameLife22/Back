package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.ex.EntityExistException;
import fr.sqli.formation.gamelife.ex.EntityNotFoundException;
import fr.sqli.formation.gamelife.ex.ParameterException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IProduitService {
    public ProduitDtoOut getProduit(Integer pIdProduit) throws ParameterException, EntityNotFoundException;
    public List<ProduitDtoOut> getProduits();
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn) throws ParameterException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, EntityExistException;
    public ProduitDtoOut updateProduit(ProduitDtoIn pProduitDtoIn) throws ParameterException, EntityNotFoundException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException;
    public Boolean deleteProduit(Integer pIdProduit) throws ParameterException, EntityNotFoundException; //TODO: add disableProduit
}