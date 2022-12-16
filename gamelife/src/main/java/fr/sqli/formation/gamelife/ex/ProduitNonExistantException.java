package fr.sqli.formation.gamelife.ex;

public class ProduitNonExistantException extends Exception {

    public ProduitNonExistantException() {
    }

    public ProduitNonExistantException(String message) {
        super(message);
    }

    public ProduitNonExistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProduitNonExistantException(Throwable cause) {
        super(cause);
    }

    public ProduitNonExistantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
