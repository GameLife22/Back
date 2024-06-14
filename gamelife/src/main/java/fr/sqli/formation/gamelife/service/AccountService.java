package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.*;
import fr.sqli.formation.gamelife.dto.response.UserResponse;
import fr.sqli.formation.gamelife.entity.UserEntity;
import fr.sqli.formation.gamelife.exception.OldPasswordException;
import fr.sqli.formation.gamelife.exception.ExistingUserException;
import fr.sqli.formation.gamelife.utility.converter.IUserConverter;
import fr.sqli.formation.gamelife.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private IUserRepository uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserEntity modificationCompte(Account dto) throws Exception{
        var user = uDao.findById(dto.getId());
        var control = uDao.findByEmail(dto.getEmail());
        if(user.isPresent()){
            UserEntity u = user.get();
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
                    throw new ExistingUserException("email deja utilise");
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
            throw new ExistingUserException("Utilisateur inexistant");
        }
    }

    public UserEntity modificationMdp(AccountPassword dto) throws Exception {
        var user = uDao.findById(dto.getId());
        if (user.isPresent()) {
            UserEntity u = uDao.findById(dto.getId()).get();
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
            throw new ExistingUserException("utilisateur inéxistant");
        }
    }

    public UserEntity modificationEtat(AccountStatus dto) throws Exception {
        var user = uDao.findById(dto.getId());
        if (user.isPresent()) {
            UserEntity u = uDao.findById(dto.getId()).get();
            if (dto.getNew_etat() == null){
                throw  new IllegalArgumentException();
            }
            u.setEtatCompte(dto.getNew_etat());
            return uDao.save(u);
        } else {
            throw new ExistingUserException("utilisateur inexistant");
        }
    }

    public boolean estRevendeur(UUID id) throws Exception {
        var user = uDao.findById(id);
        if (!user.isPresent()) {
            throw new ExistingUserException("utilisateur inexistant");
        }
        UserEntity u = uDao.findById(id).get();
        if (u.getNumSiren() != null && !u.getNumSiren().isEmpty()) {
            return true;
        }
        return false;
    }

    public UserResponse getUser(UUID id) throws Exception {
        var user = uDao.findById(id);
        if (!user.isPresent()) {
            throw new ExistingUserException("utilisateur inexistant");
        }
        UserEntity u = user.get();
        UserResponse dto = IUserConverter.fromEntity(u);

        return dto;
    }
    public List<UserEntity> getUsers() {
        List<UserEntity> users = new ArrayList<>();
        uDao.findAll().forEach(u -> users.add(u));
        return users;
    }

}
