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
            if (dto.getPrenom() != null && !dto.getPrenom().isEmpty()) {
                u.setPrenom(dto.getPrenom());
            }
            if (dto.getNom()!= null && !dto.getNom().isEmpty()) {
                u.setNom(dto.getNom());
            }
            if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
                if (control.isEmpty()) {
                    u.setEmail(dto.getEmail());
                }else if (control.get().getEmail().equals(u.getEmail())){
                    u.setEmail(dto.getEmail());
                }else {
                    throw new UtilisateurExistantException("email deja utilise");
                }
            }
            if (dto.getNumRue() != null && dto.getNumRue() == 0) {
                u.setNumRue(dto.getNumRue());
            }
            if (dto.getRue() != null && !dto.getRue().isEmpty()) {
                u.setRue(dto.getRue());
            }
            if (dto.getVille() != null && !dto.getVille().isEmpty()) {
                u.setVille(dto.getVille());
            }
            if (dto.getCodePostal() != null && dto.getCodePostal() != 0) {
                u.setCodePostal(dto.getCodePostal());
            }
            if (dto.getNumSiren() != null && !dto.getNumSiren().isEmpty()) {
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
