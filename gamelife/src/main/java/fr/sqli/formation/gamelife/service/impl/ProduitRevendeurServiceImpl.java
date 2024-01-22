package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitRevendeurDtoOut;
import fr.sqli.formation.gamelife.repository.IProduitRevendeurRepository;
import fr.sqli.formation.gamelife.service.IProduitRevendeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitRevendeurServiceImpl implements IProduitRevendeurService {
    private final IProduitRevendeurRepository dao;

    @Autowired
    public ProduitRevendeurServiceImpl(IProduitRevendeurRepository pDao) {
        dao = pDao;
    }

    @Override
    public ProduitRevendeurDtoOut getProduitRevendeur(Integer pIdUtilisateur, Integer pIdProduitRevendeur) {
        return null;
    }

    @Override
    public List<ProduitRevendeurDtoOut> getProduitsRevendeur(Integer pIdUtilisateur) {
        return null;
    }

    @Override
    public ProduitRevendeurDtoOut addProduitRevendeur(Integer pIdUtilisateur, ProduitRevendeurDtoIn pProduitRevendeurDtoIn) {
        return null;
    }

    @Override
    public ProduitRevendeurDtoOut updateProduitRevendeur(Integer pIdUtilisateur, ProduitRevendeurDtoIn pProduitRevendeurDtoIn) {
        return null;
    }
}