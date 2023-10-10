package fr.sqli.formation.gamelife.ex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthentificationException extends RuntimeException{

    private static final Logger LOG = LogManager.getLogger();

    public AuthentificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthentificationException() {
    }

    public AuthentificationException(String message) {
       super(message);
    }

    public AuthentificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthentificationException(Throwable cause) {
        super(cause);
    }
}
