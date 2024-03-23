package fr.sqli.formation.gamelife.dto.out;

import fr.sqli.formation.gamelife.dto.AbstractDto;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.util.Objects;
import java.util.UUID;

abstract class AbstractDtoOut extends AbstractDto {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    protected AbstractDtoOut() {
        super();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    @Override
    public boolean equals(Object pObject) {
        if (this == pObject) return true;
        if (pObject == null || getClass() != pObject.getClass()) return false;
        AbstractDtoOut that = (AbstractDtoOut) pObject;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AbstractDtoOut{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}

