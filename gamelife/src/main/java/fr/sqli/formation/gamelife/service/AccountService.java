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
            if (dto.getFirstName() != null && !dto.getFirstName().isEmpty()) {
                u.setFirstName(dto.getFirstName());
            }
            if (dto.getLastName()!= null && !dto.getLastName().isEmpty()) {
                u.setLastName(dto.getLastName());
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
                u.setStreetNumber(dto.getNum_rue());
            }
            if (dto.getStreet() != null && !dto.getStreet().isEmpty()) {
                u.setStreet(dto.getStreet());
            }
            if (dto.getCity() != null && !dto.getCity().isEmpty()) {
                u.setCity(dto.getCity());
            }
            if (dto.getZipCode() != null && dto.getZipCode() != 0) {
                u.setZipCode(dto.getZipCode());
            }
            if (dto.getSirenNumber() != null && !dto.getSirenNumber().isEmpty()) {
                u.setSirenNumber(dto.getSirenNumber());
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
            if (!encoder.matches(dto.getOld_mdp(), u.getPassword())){
                throw new OldPasswordException("Mot de passe incorrect");
            }
            if(encoder.matches(dto.getNew_mdp(), u.getPassword())){
                throw new OldPasswordException("Mot de passe déjà utilise");
            }
            u.setPassword(encoder.encode(dto.getNew_mdp()));
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
            u.setAccountStatus(dto.getNew_etat());
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
        if (u.getSirenNumber() != null && !u.getSirenNumber().isEmpty()) {
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
