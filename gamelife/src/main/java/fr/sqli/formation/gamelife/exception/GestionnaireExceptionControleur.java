package fr.sqli.formation.gamelife.exception;

import fr.sqli.formation.gamelife.dto.ExceptionDtoOut;
import fr.sqli.formation.gamelife.exception.commande.CommandeNotFoundException;
import fr.sqli.formation.gamelife.exception.commande.EtatCommandeInvalideException;
import fr.sqli.formation.gamelife.exception.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.exception.utilisateur.CompteDesactiveException;
import fr.sqli.formation.gamelife.exception.utilisateur.OldPasswordException;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurExistantException;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurNonExistantException;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class GestionnaireExceptionControleur {
    private static final Logger LOGGER = LoggerFactory.getLogger(GestionnaireExceptionControleur.class);

    /**
     * Gère les exceptions de type MethodArgumentNotValidException.
     *
     * @param pException L'exception de type MethodArgumentNotValidException à gérer.
     * @return Une ResponseEntity contenant une map d'erreurs avec le nom du champ et le message d'erreur correspondant, ainsi que le code de statut HTTP BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> gererExceptionValidationArgumentsRequete(MethodArgumentNotValidException pException) {
        var erreurs = new HashMap<String, String>();
        pException.getBindingResult().getAllErrors()
                .forEach(erreur -> {
                    var nomChamp = ((FieldError) erreur).getField();
                    var messageErreur = erreur.getDefaultMessage();
                    erreurs.put(nomChamp, messageErreur);
                    LOGGER.error("Erreur de validation du champ: {}, message: {}", nomChamp, messageErreur);
                });
        return new ResponseEntity<>(erreurs, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UtilisateurExistantException.class)
    public ResponseEntity<UtilisateurExistantException> exceptionHandler(UtilisateurExistantException ex){
        LOGGER.info("EXCEPTION HANDLER : UTILISATEUR EXISTANT EXCEPTION {}", ex.getMessage());
        ResponseEntity<UtilisateurExistantException> resu = new ResponseEntity<UtilisateurExistantException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(UtilisateurNonExistantException.class)
    public ResponseEntity<UtilisateurNonExistantException> exceptionHandler(UtilisateurNonExistantException ex){
        LOGGER.info("EXCEPTION HANDLER : UTILISATEUR NON EXISTANT EXCEPTION {}", ex.getMessage());
        ResponseEntity<UtilisateurNonExistantException> resu = new ResponseEntity<UtilisateurNonExistantException>(ex, HttpStatus.NOT_FOUND);
        return resu;
    }
    @ExceptionHandler(CompteDesactiveException.class)
    public ResponseEntity<CompteDesactiveException> exceptionHandler(CompteDesactiveException ex){
        LOGGER.info("EXCEPTION HANDLER : COMPTE DESACTIVE EXCEPTION {}", ex.getMessage());
        ResponseEntity<CompteDesactiveException> resu = new ResponseEntity<CompteDesactiveException>(ex, HttpStatus.FORBIDDEN);
        return resu;
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AccessDeniedException> exceptionHandler(AccessDeniedException ex){
        LOGGER.info("EXCEPTION HANDLER : ACCESS DENIED EXCEPTION", ex);
        ResponseEntity<AccessDeniedException> resu = new ResponseEntity<AccessDeniedException>(ex, HttpStatus.UNAUTHORIZED);
        return resu;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BadCredentialsException> exceptionHandler(BadCredentialsException ex){
        LOGGER.info("EXCEPTION HANDLER : BAD CREDENTIALS EXCEPTION", ex);
        ResponseEntity<BadCredentialsException> resu = new ResponseEntity<BadCredentialsException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(OldPasswordException.class)
    public ResponseEntity<OldPasswordException> exceptionHandler(OldPasswordException ex){
        LOGGER.info("EXCEPTION HANDLER : OLD PASSWORD EXCEPTION", ex);
        ResponseEntity<OldPasswordException> resu = new ResponseEntity<OldPasswordException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(ClientAbortException.class)
    public void handleLockException(ClientAbortException exception, HttpServletRequest request) {
        final String message = "ClientAbortException generated by request {} {} from remote address {} with X-FORWARDED-FOR {}";
        final String headerXFF = request.getHeader("X-FORWARDED-FOR");
        LOGGER.warn(message, request.getMethod(), request.getRequestURL(), request.getRemoteAddr(), headerXFF);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> exceptionHandler(Exception ex){
        LOGGER.info("EXCEPTION HANDLER : OTHER EXCEPTION", ex);
        ResponseEntity<Exception> resu = new ResponseEntity<Exception>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }

    @ExceptionHandler(ItemCommandeNotFoundException.class)
    public ResponseEntity<ExceptionDtoOut> handleItemCommandeNotFoundException(ItemCommandeNotFoundException ex) {
        LOGGER.info("ItemCommandeNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(new ExceptionDtoOut(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommandeNotFoundException.class)
    public ResponseEntity<ExceptionDtoOut> handleCommandeNotFoundException(CommandeNotFoundException ex) {
        LOGGER.info("CommandeNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(new ExceptionDtoOut(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProduitRevendeurException.class)
    public ResponseEntity<String> handleProduitException(ProduitRevendeurException ex) {
        LOGGER.info("Produit introuvable: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EtatCommandeInvalideException.class)
    public ResponseEntity<ExceptionDtoOut> handleEtatCommandeInvalideException(EtatCommandeInvalideException ex) {
        LOGGER.info("EtatCommandeInvalideException: {}", ex.getMessage());
        return new ResponseEntity<>(new ExceptionDtoOut(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}