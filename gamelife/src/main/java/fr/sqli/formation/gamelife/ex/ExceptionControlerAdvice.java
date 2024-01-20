package fr.sqli.formation.gamelife.ex;

import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControlerAdvice {
    private static final Logger LOG = LogManager.getLogger();
    @ExceptionHandler(UtilisateurExistantException.class)
    public ResponseEntity<UtilisateurExistantException> exceptionHandler(UtilisateurExistantException ex){
        LOG.info("EXCEPTION HANDLER : UTILISATEUR EXISTANT EXCEPTION {}", ex.getMessage());
        ResponseEntity<UtilisateurExistantException> resu = new ResponseEntity<UtilisateurExistantException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(UtilisateurNonExistantException.class)
    public ResponseEntity<UtilisateurNonExistantException> exceptionHandler(UtilisateurNonExistantException ex){
        LOG.info("EXCEPTION HANDLER : UTILISATEUR NON EXISTANT EXCEPTION {}", ex.getMessage());
        ResponseEntity<UtilisateurNonExistantException> resu = new ResponseEntity<UtilisateurNonExistantException>(ex, HttpStatus.NOT_FOUND);
        return resu;
    }
    @ExceptionHandler(CompteDesactiveException.class)
    public ResponseEntity<CompteDesactiveException> exceptionHandler(CompteDesactiveException ex){
        LOG.info("EXCEPTION HANDLER : COMPTE DESACTIVE EXCEPTION {}", ex.getMessage());
        ResponseEntity<CompteDesactiveException> resu = new ResponseEntity<CompteDesactiveException>(ex, HttpStatus.FORBIDDEN);
        return resu;
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AccessDeniedException> exceptionHandler(AccessDeniedException ex){
        LOG.info("EXCEPTION HANDLER : ACCESS DENIED EXCEPTION", ex);
        ResponseEntity<AccessDeniedException> resu = new ResponseEntity<AccessDeniedException>(ex, HttpStatus.UNAUTHORIZED);
        return resu;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BadCredentialsException> exceptionHandler(BadCredentialsException ex){
        LOG.info("EXCEPTION HANDLER : BAD CREDENTIALS EXCEPTION", ex);
        ResponseEntity<BadCredentialsException> resu = new ResponseEntity<BadCredentialsException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(OldPasswordException.class)
    public ResponseEntity<OldPasswordException> exceptionHandler(OldPasswordException ex){
        LOG.info("EXCEPTION HANDLER : OLD PASSWORD EXCEPTION", ex);
        ResponseEntity<OldPasswordException> resu = new ResponseEntity<OldPasswordException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(ClientAbortException.class)
    public void handleLockException(ClientAbortException exception, HttpServletRequest request) {
        final String message = "ClientAbortException generated by request {} {} from remote address {} with X-FORWARDED-FOR {}";
        final String headerXFF = request.getHeader("X-FORWARDED-FOR");
        LOG.warn(message, request.getMethod(), request.getRequestURL(), request.getRemoteAddr(), headerXFF);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> exceptionHandler(Exception ex){
        LOG.info("EXCEPTION HANDLER : OTHER EXCEPTION", ex);
        ResponseEntity<Exception> resu = new ResponseEntity<Exception>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(CommandeNotFoundException.class)
    public ResponseEntity<String> handlePanierNotFoundException(CommandeNotFoundException ex) {
        LOG.info("Panier introuvable: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProduitRevendeutException.class)
    public ResponseEntity<String> handleProduitException(ProduitRevendeutException ex) {
        LOG.info("Produit introuvable: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}