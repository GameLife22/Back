package fr.sqli.formation.gamelife.ex;

public class OldPasswordException extends Exception{



    public OldPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OldPasswordException() {
    }

    public OldPasswordException(String message) {
        super(message);
    }

    public OldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public OldPasswordException(Throwable cause) {
        super(cause);
    }
}