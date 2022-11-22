package fr.sqli.formation.gamelife.ex;

public class CompteDesactiveException extends Exception{
    public CompteDesactiveException() {
    }

    public CompteDesactiveException(String message) {
        super(message);
    }

    public CompteDesactiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompteDesactiveException(Throwable cause) {
        super(cause);
    }

    public CompteDesactiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
