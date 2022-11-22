package fr.sqli.formation.gamelife.dto;

public class GestionEtatDto {

    public GestionEtatDto() {
    }

    public GestionEtatDto(Integer id, String new_etat) {
        this.new_etat = new_etat;
        this.id = id;
    }

    private String new_etat;
    private Integer id;

    public String getNew_etat() {
        return new_etat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}