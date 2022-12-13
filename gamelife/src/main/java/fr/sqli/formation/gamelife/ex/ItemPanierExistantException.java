package fr.sqli.formation.gamelife.ex;

public class ItemPanierExistantException extends Exception{
    public ItemPanierExistantException() {
    }

    public ItemPanierExistantException(String message) {
        super(message);
    }

    public ItemPanierExistantException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemPanierExistantException(Throwable cause) {
        super(cause);
    }

    public ItemPanierExistantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
