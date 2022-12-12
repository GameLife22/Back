package fr.sqli.formation.gamelife.ex;

public class PanierExistantException extends Exception{
    public PanierExistantException() {
    }

    public PanierExistantException(String message) {
        super(message);
    }

    public PanierExistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public PanierExistantException(Throwable cause) {
        super(cause);
    }

    public PanierExistantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
