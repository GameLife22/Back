package fr.sqli.formation.gamelife.service;

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

    public UtilisateurEntity modificationCompte(String nom,String prenom,String newEmail,String oldEmail,Integer numRue,String rue,String ville, Integer codePostal, String role,String numSiren,String etat) throws Exception{
        var user = uDao.findByEmail(oldEmail);
        if(user.isPresent()){
            UtilisateurEntity u = uDao.findByEmail(oldEmail).get();
            u.setPrenom(prenom);
            u.setNom(nom);
            u.setEmail(newEmail);
            u.setNumRue(numRue);
            u.setRue(rue);
            u.setVille(ville);
            u.setCodePostal(codePostal);
            u.setNumSiren(numSiren);
            u.setEtatCompte(etat);
            u.setRole(role);
            return uDao.save(u);
        }else {
            throw new UtilisateurExistantException("Utilisateur inexistant");
        }
    }

    public UtilisateurEntity modificationMdp(String email, String oldMdp,String newMdp) throws Exception {
        var user = uDao.findByEmail(email);
        if (user.isPresent()) {
            UtilisateurEntity u = uDao.findByEmail(email).get();
            if (u.getMdp().equals(oldMdp)) {
                u.setMdp(encoder.encode(newMdp));
                return uDao.save(u);
            } else {
                throw new OldPasswordException("Mauvais mot de passe");
            }
        } else {
            throw new UtilisateurExistantException("utilisateur inexistant");
        }
    }

    public UtilisateurEntity modificationEtat(String email, String etat) throws Exception {
        var user = uDao.findByEmail(email);
        if (user.isPresent()) {
            UtilisateurEntity u = uDao.findByEmail(email).get();
            u.setEtatCompte(etat);
            return uDao.save(u);
        } else {
            throw new UtilisateurExistantException("utilisateur inexistant");

        }
    }
}
