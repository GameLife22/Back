package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository repository;
    private static final Logger LOG = LogManager.getLogger();

    public UtilisateurEntity getUtilisateurById(int id) throws Exception{
        if(id > 0){
            LOG.info(id);
            var utilisateur = this.repository.findById(id);

            if(utilisateur.isPresent()){
                return utilisateur.get();
            }else{
                throw new UtilisateurExistantException();
            }
        }
        throw new IllegalArgumentException();
        //return this.repository.findById(id).get();
    }
}
