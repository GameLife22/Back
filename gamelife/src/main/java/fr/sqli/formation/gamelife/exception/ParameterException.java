package fr.sqli.formation.gamelife.exception;

import java.io.Serial;

public class ParameterException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }
}