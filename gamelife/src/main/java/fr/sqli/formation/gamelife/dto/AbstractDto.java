package fr.sqli.formation.gamelife.dto;

import java.io.Serial;
import java.io.Serializable;

public abstract class AbstractDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected AbstractDto() {
        super();
    }
}