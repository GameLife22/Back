package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.repository.IProduitRepository;
import fr.sqli.formation.gamelife.service.IProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitServiceImpl implements IProduitService {
    private final IProduitRepository produitDao;

    @Autowired
    public ProduitServiceImpl(IProduitRepository pProduitDao) {
        produitDao = pProduitDao;
    }

    @Override
    public ProduitDtoOut getProduit(Integer pIdProduit) {
        return null;
    }

    @Override
    public List<ProduitDtoOut> getProduits() {
        return null;
    }

    @Override
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn) {
        return null;
    }

    @Override
    public ProduitDtoOut updateProduit(Integer pIdProduit, ProduitDtoIn pProduitDtoIn) {
        return null;
    }

    @Override
    public Boolean deleteProduit(Integer pIdProduit) {
        return null;
    }
}
