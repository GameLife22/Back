package fr.sqli.formation.gamelife.ex.panier;

public class ItemPanierNotFoundException extends Exception{
    public ItemPanierNotFoundException(String message) {
        super(message);
    }

    public ItemPanierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
