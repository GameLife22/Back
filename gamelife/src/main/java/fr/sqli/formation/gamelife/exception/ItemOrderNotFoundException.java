package fr.sqli.formation.gamelife.exception;

public class ItemOrderNotFoundException extends Exception{
    public ItemOrderNotFoundException(String message) {
        super(message);
    }

    public ItemOrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
