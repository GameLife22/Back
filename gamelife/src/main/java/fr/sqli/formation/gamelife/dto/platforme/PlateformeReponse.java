package fr.sqli.formation.gamelife.dto.platforme;

import java.io.Serial;
import java.util.UUID;

public class PlateformeReponse {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String libelle;

    public PlateformeReponse() {
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
        final StringBuffer sb = new StringBuffer("PlateformeDtoOut{");
        sb.append("id=").append(id);
        sb.append(", libelle='").append(libelle).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
