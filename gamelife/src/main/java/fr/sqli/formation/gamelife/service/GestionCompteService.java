package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.GestionCompteDto;
import fr.sqli.formation.gamelife.dto.GestionEtatDto;
import fr.sqli.formation.gamelife.dto.GestionMdpDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GestionCompteService {
    @Autowired
    private UtilisateurRepository uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public UtilisateurEntity modificationCompte(GestionCompteDto dto) throws Exception{
        var user = uDao.findById(dto.getId());
        if(user.isPresent()){
            UtilisateurEntity u = uDao.findById(dto.getId()).get();
            u.setPrenom(dto.getPrenom());
            u.setNom(dto.getNom());
            u.setEmail(dto.getEmail());
            u.setNumRue(dto.getNumRue());
            u.setRue(dto.getRue());
            u.setVille(dto.getVille());
            u.setCodePostal(dto.getCodePostal());
            u.setNumSiren(dto.getNumSiren());
            return uDao.save(u);
        }else {
            throw new UtilisateurExistantException("Utilisateur inexistant");
        }
    }

    public UtilisateurEntity modificationMdp(GestionMdpDto dto) throws Exception {
        var user = uDao.findById(dto.getId());
        if (user.isPresent()) {
            UtilisateurEntity u = uDao.findById(dto.getId()).get();
            u.setMdp(encoder.encode(dto.getNew_mdp()));
            return uDao.save(u);
        } else {
            throw new UtilisateurExistantException("utilisateur inexistant");
        }
    }

    public UtilisateurEntity modificationEtat(GestionEtatDto dto) throws Exception {
        var user = uDao.findById(dto.getId());
        if (user.isPresent()) {
            UtilisateurEntity u = uDao.findById(dto.getId()).get();
            u.setEtatCompte(dto.getNew_etat());
            return uDao.save(u);
        } else {
            throw new UtilisateurExistantException("utilisateur inexistant");
        }
    }

    public boolean estRevendeur(int id) throws Exception {
        var user = uDao.findById(id);
        if (!user.isPresent()) {
            throw new UtilisateurExistantException("utilisateur inexistant");
        }
        UtilisateurEntity u = uDao.findById(id).get();
        if (u.getNumSiren() != null) {
            return true;
        }
        return false;
    }
}
