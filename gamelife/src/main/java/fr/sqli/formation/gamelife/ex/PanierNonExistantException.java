package fr.sqli.formation.gamelife.ex;

public class PanierNonExistantException extends Exception{
    public PanierNonExistantException() {
    }

    public PanierNonExistantException(String message) {
        super(message);
    }

    public PanierNonExistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public PanierNonExistantException(Throwable cause) {
        super(cause);
    }

    public PanierNonExistantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
