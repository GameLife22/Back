package fr.sqli.formation.gamelife.ex;

public class UtilisateurNonExistantException extends Exception{
    public UtilisateurNonExistantException() {
    }

    public UtilisateurNonExistantException(String message) {
        super(message);
    }

    public UtilisateurNonExistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilisateurNonExistantException(Throwable cause) {
        super(cause);
    }

    public UtilisateurNonExistantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
