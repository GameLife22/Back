package fr.sqli.formation.gamelife.ex.commande;

public class CommandeNotFoundException extends Exception {

    //Commande Message erreur
    public CommandeNotFoundException(String message) {
        super(message);
    }

    public CommandeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
