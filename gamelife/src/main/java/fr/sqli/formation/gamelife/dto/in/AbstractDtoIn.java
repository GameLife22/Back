package fr.sqli.formation.gamelife.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.sqli.formation.gamelife.dto.AbstractDto;

import java.io.Serial;

public abstract class AbstractDtoIn extends AbstractDto {
    @Serial
    private static final long serialVersionUID = 1L;

    protected AbstractDtoIn() {
        super();
    }

    @JsonIgnore
    public abstract void validate();

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" {}");
        return sb.toString();
    }
}

