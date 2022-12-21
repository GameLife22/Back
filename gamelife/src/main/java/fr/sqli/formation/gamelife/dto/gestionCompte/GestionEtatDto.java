package fr.sqli.formation.gamelife.dto.gestionCompte;

public class GestionEtatDto {

    public GestionEtatDto() {
    }

    public GestionEtatDto(Integer id, Integer new_etat) {
        this.new_etat = new_etat;
        this.id = id;
    }

    private Integer new_etat;
    private Integer id;

    public Integer getNew_etat() {
        return new_etat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}