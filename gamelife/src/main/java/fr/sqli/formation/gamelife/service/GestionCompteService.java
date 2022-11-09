package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.GestionCompteDto;
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
            var user = uDao.findByEmail(dto.getOld_email());
            if(user.isPresent()){
                var u = uDao.findByEmail(dto.getOld_email()).get();
                u.setPrenom(dto.getPrenom());
                u.setNom(dto.getNom());
                u.setEmail(dto.getNew_email());
                u.setVille(dto.getVille());
                u.setRue(dto.getRue());
                u.setNumRue(dto.getNum_rue());
                u.setNumSiren(dto.getNum_siren());
                return uDao.save(u);
            }else {
                throw new UtilisateurExistantException("Modification du compte impossible, utilisateur inexistant");
            }
    }

    public UtilisateurEntity modificationMdp(GestionMdpDto dto) throws Exception{
        var user = uDao.findByEmail(dto.getEmail());
        if(user.isPresent()){
            UtilisateurEntity u = uDao.findByEmail(dto.getEmail()).get();
            if (u.getMdp().equals(dto.getOld_mdp())){
                u.setMdp(encoder.encode(dto.getNew_mdp()));
                return uDao.save(u);
            }else {
                throw new OldPasswordException("Mauvais mot de passe");
            }
        }else {
            throw new UtilisateurExistantException("utilisateur inexistant");
        }
    }
}
