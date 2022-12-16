package fr.sqli.formation.gamelife.ex;

public class ProduitExistantException extends Exception {

    public ProduitExistantException() {
    }

    public ProduitExistantException(String message) {
        super(message);
    }

    public ProduitExistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProduitExistantException(Throwable cause) {
        super(cause);
    }

    public ProduitExistantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
