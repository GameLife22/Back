package fr.sqli.formation.gamelife.ex.panier;

public class PanierNotFoundException extends Exception {

    //Panier Message erreur
    public PanierNotFoundException(String message) {
        super(message);
    }

    public PanierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
