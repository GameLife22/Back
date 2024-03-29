package fr.sqli.formation.gamelife.ex;


public class ProduitRevendeutException extends Exception {

    public ProduitRevendeutException() {
    }

    public ProduitRevendeutException(String message) {
        super(message);
    }

    public ProduitRevendeutException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProduitRevendeutException(Throwable cause) {
        super(cause);
    }

    public ProduitRevendeutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
