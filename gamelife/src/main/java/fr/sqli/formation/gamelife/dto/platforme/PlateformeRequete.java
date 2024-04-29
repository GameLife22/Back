package fr.sqli.formation.gamelife.dto.platforme;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class PlateformeRequete {
    private UUID id;

    @NotEmpty
    private String libelle;

    public PlateformeRequete() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PlateformeDtoIn{");
        sb.append("id=").append(id);
        sb.append(", libelle='").append(libelle).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
