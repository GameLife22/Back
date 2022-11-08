package fr.sqli.formation.gamelife.ex;

public class UtilisateurExistantException extends Exception{
    public UtilisateurExistantException() {
    }

    public UtilisateurExistantException(String message) {
        super(message);
    }

    public UtilisateurExistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilisateurExistantException(Throwable cause) {
        super(cause);
    }

    public UtilisateurExistantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
