package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InscriptionService {
    @Autowired
    private UtilisateurRepository uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public UtilisateurEntity inscription(String nom,String prenom,String pwd,String email,String ville,Integer num_rue,String rue,String role,String num_Siren,String etat) throws Exception{
            UtilisateurEntity.validate(nom,prenom,pwd,email,ville,num_rue,rue,role,num_Siren,etat);
            var newUser = uDao.findByEmail(email);
            if(newUser.isEmpty()){
                UtilisateurEntity u = new UtilisateurEntity();
                u.setPrenom(prenom);
                u.setNom(nom);
                u.setMdp(encoder.encode(pwd));
                u.setEmail(email);
                u.setVille(ville);
                u.setRue(rue);
                u.setNumRue(num_rue);
                u.setNumSiren(num_Siren);
                u.setEtatCompte(etat);
                u.setRole(role);
                return uDao.save(u);
            }else {
                throw new UtilisateurExistantException("Utilisateur deja enregistre");
            }
    }
}
