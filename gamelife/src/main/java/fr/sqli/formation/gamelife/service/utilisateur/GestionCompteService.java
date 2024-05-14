package fr.sqli.formation.gamelife.service.utilisateur;

import fr.sqli.formation.gamelife.dto.gestionCompte.*;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import fr.sqli.formation.gamelife.exception.utilisateur.OldPasswordException;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurExistantException;
import fr.sqli.formation.gamelife.dao.IUtilisateurDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GestionCompteService {
    @Autowired
    private IUtilisateurDao uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public UtilisateurEntite modificationCompte(GestionCompteDto dto) throws Exception{
        var user = uDao.findById(dto.getId());
        var control = uDao.findByEmail(dto.getEmail());
        if(user.isPresent()){
            UtilisateurEntite u = user.get();
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
            if (dto.getNum_rue() != null && dto.getNum_rue() != 0) {
                u.setNumRue(dto.getNum_rue());
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

    public UtilisateurEntite modificationMdp(GestionMdpDto dto) throws Exception {
        var user = uDao.findById(dto.getId());
        if (user.isPresent()) {
            UtilisateurEntite u = uDao.findById(dto.getId()).get();
            if(dto.getNew_mdp() == null || dto.getNew_mdp().isEmpty() || dto.getOld_mdp() == null || dto.getOld_mdp().isEmpty()){
                throw new IllegalArgumentException("Champs vide ou null");
            }
            if (!encoder.matches(dto.getOld_mdp(), u.getMdp())){
                throw new OldPasswordException("Mot de passe incorrect");
            }
            if(encoder.matches(dto.getNew_mdp(), u.getMdp())){
                throw new OldPasswordException("Mot de passe déjà utilise");
            }
            u.setMdp(encoder.encode(dto.getNew_mdp()));
            return uDao.save(u);
        } else {
            throw new UtilisateurExistantException("utilisateur inéxistant");
        }
    }

    public UtilisateurEntite modificationEtat(GestionEtatDto dto) throws Exception {
        var user = uDao.findById(dto.getId());
        if (user.isPresent()) {
            UtilisateurEntite u = uDao.findById(dto.getId()).get();
            if (dto.getNew_etat() == null){
                throw  new IllegalArgumentException();
            }
            u.setEtatCompte(dto.getNew_etat());
            return uDao.save(u);
        } else {
            throw new UtilisateurExistantException("utilisateur inexistant");
        }
    }

    public boolean estRevendeur(UUID id) throws Exception {
        var user = uDao.findById(id);
        if (!user.isPresent()) {
            throw new UtilisateurExistantException("utilisateur inexistant");
        }
        UtilisateurEntite u = uDao.findById(id).get();
        if (u.getNumSiren() != null && !u.getNumSiren().isEmpty()) {
            return true;
        }
        return false;
    }

    public UtilisateurEntite getUser(UUID id) throws Exception {
        var user = uDao.findById(id);
        if (!user.isPresent()) {
            throw new UtilisateurExistantException("utilisateur inexistant");
        }
        UtilisateurEntite u = user.get();
        UserDtoOut dto = UserDtoHandler.fromEntity(u);

        return u;
    }
    public List<UtilisateurEntite> getUsers() {
        List<UtilisateurEntite> users = new ArrayList<>();
        uDao.findAll().forEach(u -> users.add(u));
        return users;
    }

}
