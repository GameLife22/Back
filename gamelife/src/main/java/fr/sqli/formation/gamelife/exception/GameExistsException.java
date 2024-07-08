package fr.sqli.formation.gamelife.exception;

public class GameExistsException extends Exception {

    public GameExistsException() {
        super("Game exists");
    }

    public GameExistsException(String message) {
        super(message);
    }

    public GameExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameExistsException(Throwable cause) {
        super(cause);
    }

    protected GameExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
