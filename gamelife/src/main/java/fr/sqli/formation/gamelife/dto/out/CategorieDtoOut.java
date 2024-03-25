package fr.sqli.formation.gamelife.dto.out;

import java.io.Serial;

public class CategorieDtoOut extends AbstractDtoOut {
    @Serial
    private static final long serialVersionUID = 1L;

    private String libelle;

    public CategorieDtoOut() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CategorieDtoOut{");
        sb.append("libelle='").append(libelle).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
