package fr.sqli.formation.gamelife.exception;

public class OrderNotFoundException extends Exception {

    //Commande Message erreur
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
