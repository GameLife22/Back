package fr.sqli.formation.gamelife.dto.gestionCompte;

import java.util.UUID;

public class GestionEtatDto {

    private UUID id;
    private Boolean new_etat;

    public GestionEtatDto() {
    }

    public GestionEtatDto(UUID id, Boolean new_etat) {
        this.new_etat = new_etat;
        this.id = id;
    }

    public Boolean getNew_etat() {
        return new_etat;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }
}