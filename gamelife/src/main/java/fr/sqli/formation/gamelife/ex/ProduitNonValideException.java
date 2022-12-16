package fr.sqli.formation.gamelife.ex;

public class ProduitNonValideException extends Exception {

    public ProduitNonValideException() {
    }

    public ProduitNonValideException(String message) {
        super(message);
    }

    public ProduitNonValideException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProduitNonValideException(Throwable cause) {
        super(cause);
    }

    public ProduitNonValideException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
