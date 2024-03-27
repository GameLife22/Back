package fr.sqli.formation.gamelife.dto.in;

import fr.sqli.formation.gamelife.dto.AbstractDto;

import java.io.Serial;
import java.util.Objects;
import java.util.UUID;

abstract class AbstractDtoIn extends AbstractDto {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    protected AbstractDtoIn() {
        super();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        this.id = pId;
    }

    @Override
    public boolean equals(Object pObject) {
        if (this == pObject) return true;
        if (pObject == null || getClass() != pObject.getClass()) return false;
        AbstractDtoIn that = (AbstractDtoIn) pObject;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AbstractDtoIn{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}

