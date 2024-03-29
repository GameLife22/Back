package fr.sqli.formation.gamelife.ex.commande;

public class ItemCommandeNotFoundException extends Exception{
    public ItemCommandeNotFoundException(String message) {
        super(message);
    }

    public ItemCommandeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
