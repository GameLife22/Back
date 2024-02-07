package fr.sqli.formation.gamelife.dto.gestionCompte;

public class GestionEtatDto {

    private Integer id;
    private Boolean new_etat;

    public GestionEtatDto() {
    }

    public GestionEtatDto(Integer id, Boolean new_etat) {
        this.new_etat = new_etat;
        this.id = id;
    }

    public Boolean getNew_etat() {
        return new_etat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}