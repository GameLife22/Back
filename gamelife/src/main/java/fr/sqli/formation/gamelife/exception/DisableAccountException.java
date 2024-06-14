package fr.sqli.formation.gamelife.exception;

public class DisableAccountException extends Exception{
    public DisableAccountException() {
    }

    public DisableAccountException(String message) {
        super(message);
    }

    public DisableAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisableAccountException(Throwable cause) {
        super(cause);
    }

    public DisableAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
