package fr.sqli.formation.gamelife.exception;

public class NonExistentUserException extends Exception {
    public NonExistentUserException() {
    }

    public NonExistentUserException(String message) {
        super(message);
    }

    public NonExistentUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExistentUserException(Throwable cause) {
        super(cause);
    }

    public NonExistentUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
