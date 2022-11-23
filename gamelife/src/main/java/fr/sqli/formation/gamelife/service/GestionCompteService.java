package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.GestionCompteDto;
import fr.sqli.formation.gamelife.dto.GestionEtatDto;
import fr.sqli.formation.gamelife.dto.GestionMdpDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.OldPasswordException;
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
        var control = uDao.findByEmail(dto.getEmail());
        if(user.isPresent()){
            UtilisateurEntity u = uDao.findById(dto.getId()).get();
            if (!dto.getPrenom().isEmpty() || dto.getPrenom() != null) {
                u.setPrenom(dto.getPrenom());
            }
            if (!dto.getNom().isEmpty() || dto.getNom() != null) {
                u.setNom(dto.getNom());
            }
            if (!dto.getEmail().isEmpty() || dto.getEmail() != null) {
                if (control.isEmpty()) {
                    u.setEmail(dto.getEmail());
                }else if (control.get().getEmail().equals(u.getEmail())){
                    u.setEmail(dto.getEmail());
                }else {
                    throw new UtilisateurExistantException("email deja utilise");
                }
            }
            if (dto.getNumRue() != 0 || dto.getNumRue() != null) {
                u.setNumRue(dto.getNumRue());
            }
            if (!dto.getRue().isEmpty() || dto.getRue() != null) {
                u.setRue(dto.getRue());
            }
            if (!dto.getVille().isEmpty() || dto.getVille() != null) {
                u.setVille(dto.getVille());
            }
            if (dto.getCodePostal() != 0 || dto.getCodePostal() != null) {
                u.setCodePostal(dto.getCodePostal());
            }
            if (!dto.getNumSiren().isEmpty() || dto.getNumSiren() != null) {
                u.setNumSiren(dto.getNumSiren());
            }
            return uDao.save(u);
        }else {
            throw new UtilisateurExistantException("Utilisateur inexistant");
        }
    }

    public UtilisateurEntity modificationMdp(GestionMdpDto dto) throws Exception {
        var user = uDao.findById(dto.getId());
        if (user.isPresent()) {
            UtilisateurEntity u = uDao.findById(dto.getId()).get();
            if(encoder.matches(dto.getNew_mdp(), u.getMdp())){
                throw new OldPasswordException("mot de passe deja utilise");
            }
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
        if (u.getNumSiren() != null && !u.getNumSiren().isEmpty()) {
            return true;
        }
        return false;
    }
}
