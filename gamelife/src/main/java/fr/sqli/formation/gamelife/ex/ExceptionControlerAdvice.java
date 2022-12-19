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
        LOG.info("EXCEPTION HANDLER : ITEM PANIER EXISTANT EXCEPTION");
        ResponseEntity<ItemPanierExistantException> resu = new ResponseEntity<ItemPanierExistantException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }
    @ExceptionHandler(ItemPanierNonExistantException.class)
    public ResponseEntity<ItemPanierNonExistantException> exceptionHandler(ItemPanierNonExistantException ex){
        LOG.info("EXCEPTION HANDLER : ITEM PANIER NON EXISTANT EXCEPTION");
        ResponseEntity<ItemPanierNonExistantException> resu = new ResponseEntity<ItemPanierNonExistantException>(ex, HttpStatus.BAD_REQUEST);

        return resu;
    }

    @ExceptionHandler(UtilisateurExistantException.class)
    public ResponseEntity<UtilisateurExistantException> exceptionHandler(UtilisateurExistantException ex){
        LOG.info("EXCEPTION HANDLER : UTILISATEUR EXISTANT EXCEPTION", ex);
        ResponseEntity<UtilisateurExistantException> resu = new ResponseEntity<UtilisateurExistantException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }

    @ExceptionHandler(UtilisateurNonExistantException.class)
    public ResponseEntity<UtilisateurNonExistantException> exceptionHandler(UtilisateurNonExistantException ex){
        LOG.info("EXCEPTION HANDLER : UTILISATEUR NON EXISTANT EXCEPTION", ex);
        ResponseEntity<UtilisateurNonExistantException> resu = new ResponseEntity<UtilisateurNonExistantException>(ex, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(ProduitExistantException.class)
    public ResponseEntity<ProduitExistantException> exceptionHandler(ProduitExistantException ex){
        LOG.info("EXCEPTION HANDLER : PRODUIT EXISTANT EXCEPTION", ex);
        ResponseEntity<ProduitExistantException> resu = new ResponseEntity<ProduitExistantException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }

    @ExceptionHandler(ProduitNonExistantException.class)
    public ResponseEntity<ProduitNonExistantException> exceptionHandler(ProduitNonExistantException ex){
        LOG.info("EXCEPTION HANDLER : PRODUIT N'EXISTE PAS EXCEPTION", ex);
        ResponseEntity<ProduitNonExistantException> resu = new ResponseEntity<ProduitNonExistantException>(ex, HttpStatus.NOT_FOUND);
        return resu;
    }

    @ExceptionHandler(ProduitNonValideException.class)
    public ResponseEntity<ProduitNonValideException> exceptionHandler(ProduitNonValideException ex){
        LOG.info("EXCEPTION HANDLER : PRODUIT INVALIDE EXCEPTION", ex);
        ResponseEntity<ProduitNonValideException> resu = new ResponseEntity<ProduitNonValideException>(ex, HttpStatus.BAD_REQUEST);
        return resu;
    }

    @ExceptionHandler(ProduitsNonExistantsException.class)
    public ResponseEntity<ProduitsNonExistantsException> exceptionHandler(ProduitsNonExistantsException ex){
        LOG.info("EXCEPTION HANDLER : PRODUIT INTROUVABLE EXCEPTION", ex);
        ResponseEntity<ProduitsNonExistantsException> resu = new ResponseEntity<ProduitsNonExistantsException>(ex, HttpStatus.NO_CONTENT);
        return resu;
    }

}