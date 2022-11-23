package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.InscriptionDto;
import fr.sqli.formation.gamelife.dto.InscriptionDtoHandler;
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

    public UtilisateurEntity inscription(InscriptionDto dto) throws Exception{
            UtilisateurEntity.validate(dto.getNom(),dto.getPrenom(), dto.getMdp(), dto.getEmail(),dto.getVille(),dto.getNum_rue(),dto.getRue(), dto.getRole(), dto.getNum_siren(), dto.getEtat(), dto.getCode_postal());
            var newUser = uDao.findByEmail(dto.getEmail());
            if(newUser.isEmpty()){
                UtilisateurEntity u = InscriptionDtoHandler.fromDto(dto);
                u.setMdp(encoder.encode(u.getMdp()));
                return uDao.save(u);
            }else {
                if(newUser.get().getEtatCompte().equals("desactive")){
                    newUser.get().setEtatCompte("active");
                    newUser.get().setNom(dto.getNom());
                    newUser.get().setPrenom(dto.getPrenom());
                    newUser.get().setMdp(encoder.encode(dto.getMdp()));
                    newUser.get().setEmail(dto.getEmail());
                    newUser.get().setVille(dto.getVille());
                    newUser.get().setCodePostal(dto.getCode_postal());
                    newUser.get().setRue(dto.getRue());
                    newUser.get().setNumRue(dto.getNum_rue());
                    newUser.get().setRole(dto.getRole());
                    newUser.get().setNumSiren(dto.getNum_siren());
                     return uDao.save(newUser.get());
                }else{
                    throw new UtilisateurExistantException("Utilisateur deja enregistre");
                }
            }
    }
}
