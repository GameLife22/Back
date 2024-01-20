package fr.sqli.formation.gamelife.ex;

public class PanierNotFoundException extends Exception {

    //Panier Message erreur
    public PanierNotFoundException(String message) {
        super(message);
    }

    public PanierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
