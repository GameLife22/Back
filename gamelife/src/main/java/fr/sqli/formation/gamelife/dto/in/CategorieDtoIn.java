package fr.sqli.formation.gamelife.dto.in;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serial;

public class CategorieDtoIn extends AbstractDtoIn {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(max = 25)
    @NotEmpty
    private String libelle;

    public CategorieDtoIn() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CategorieDtoIn{");
        sb.append("libelle='").append(libelle).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
