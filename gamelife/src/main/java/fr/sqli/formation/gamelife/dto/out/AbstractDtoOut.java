package fr.sqli.formation.gamelife.dto.out;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.sqli.formation.gamelife.dto.AbstractDto;

import java.io.Serial;
import java.util.Objects;

abstract class AbstractDtoOut extends AbstractDto {
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
    public boolean equals(Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;
        AbstractDtoOut that = (AbstractDtoOut) pO;
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

