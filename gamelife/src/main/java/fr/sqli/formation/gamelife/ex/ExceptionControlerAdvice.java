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
}
