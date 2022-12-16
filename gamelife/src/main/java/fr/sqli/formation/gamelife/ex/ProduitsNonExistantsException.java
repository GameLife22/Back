package fr.sqli.formation.gamelife.ex;

public class ProduitsNonExistantsException extends Exception {

    public ProduitsNonExistantsException() {
    }

    public ProduitsNonExistantsException(String message) {
        super(message);
    }

    public ProduitsNonExistantsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProduitsNonExistantsException(Throwable cause) {
        super(cause);
    }

    public ProduitsNonExistantsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
