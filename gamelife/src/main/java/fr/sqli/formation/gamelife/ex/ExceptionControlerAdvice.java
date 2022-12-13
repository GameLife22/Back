package fr.sqli.formation.gamelife.ex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControlerAdvice {
    private static final Logger LOG = LogManager.getLogger();

    @ExceptionHandler(AuthentificationException.class)
    public ResponseEntity<AuthentificationException> exceptionHandler(AuthentificationException ex){
        LOG.info("EXCEPTION HANDLER : AUTHENTIFICATION EXCEPTION", ex);
        ResponseEntity<AuthentificationException> resu = new ResponseEntity<AuthentificationException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(CompteDesactiveException.class)
    public ResponseEntity<CompteDesactiveException> exceptionHandler(CompteDesactiveException ex){
        LOG.info("EXCEPTION HANDLER : COMPTE DESACTIVE EXCEPTION");
        ResponseEntity<CompteDesactiveException> resu = new ResponseEntity<CompteDesactiveException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(ItemPanierExistantException.class)
    public ResponseEntity<ItemPanierExistantException> exceptionHandler(ItemPanierExistantException ex){
        LOG.info("EXCEPTION HANDLER : ItemPanier EXISTANT EXCEPTION");
        ResponseEntity<ItemPanierExistantException> resu = new ResponseEntity<ItemPanierExistantException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(ItemPanierNonExistantException.class)
    public ResponseEntity<ItemPanierNonExistantException> exceptionHandler(ItemPanierNonExistantException ex){
        LOG.info("EXCEPTION HANDLER : ItemPanier NON EXISTANT EXCEPTION");
        ResponseEntity<ItemPanierNonExistantException> resu = new ResponseEntity<ItemPanierNonExistantException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }

    @ExceptionHandler(UtilisateurExistantException.class)
    public ResponseEntity<UtilisateurExistantException> exceptionHandler(UtilisateurExistantException ex){
        LOG.info("EXCEPTION HANDLER : UTILISATEUR EXISTANT EXCEPTION", ex);
        ResponseEntity<UtilisateurExistantException> resu = new ResponseEntity<UtilisateurExistantException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> exceptionHandler(Exception ex){
        LOG.info("EXCEPTION HANDLER : OTHER EXCEPTION", ex);
        ResponseEntity<Exception> resu = new ResponseEntity<Exception>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(OldPasswordException.class)
    public ResponseEntity<OldPasswordException> exceptionHandler(OldPasswordException ex){
        LOG.info("EXCEPTION HANDLER : OLD PASSWORD EXCEPTION", ex);
        ResponseEntity<OldPasswordException> resu = new ResponseEntity<OldPasswordException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
}