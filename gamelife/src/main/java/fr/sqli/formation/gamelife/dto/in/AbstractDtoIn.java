package fr.sqli.formation.gamelife.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.sqli.formation.gamelife.dto.AbstractDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serial;
import java.util.Objects;

abstract class AbstractDtoIn extends AbstractDto {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    @Positive
    private Integer id;

    protected AbstractDtoIn() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer pId) {
        id = pId;
    }

    @Override
    public boolean equals(Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;
        AbstractDtoIn that = (AbstractDtoIn) pO;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" {}");
        return sb.toString();
    }
}

