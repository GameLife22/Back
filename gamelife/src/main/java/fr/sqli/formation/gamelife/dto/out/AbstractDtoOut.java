package fr.sqli.formation.gamelife.dto.out;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.sqli.formation.gamelife.dto.AbstractDto;

import java.io.Serial;

public abstract class AbstractDtoOut extends AbstractDto {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    protected AbstractDtoOut() {
        super();
    }

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer pId) {
        id = pId;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" {}");
        return sb.toString();
    }
}

