package fr.sqli.formation.gamelife.ex;

public class ItemPanierNonExistantException extends Exception{
    public ItemPanierNonExistantException() {
    }

    public ItemPanierNonExistantException(String message) {
        super(message);
    }

    public ItemPanierNonExistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemPanierNonExistantException(Throwable cause) {
        super(cause);
    }

    public ItemPanierNonExistantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
