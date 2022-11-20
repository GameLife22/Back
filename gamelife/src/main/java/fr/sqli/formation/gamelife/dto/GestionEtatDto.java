package fr.sqli.formation.gamelife.dto;

public class GestionEtatDto {

    public GestionEtatDto(String new_etat, String email) {
        this.new_etat = new_etat;
        this.email = email;
    }

    private String new_etat;
    private String email;

    public String getNew_etat() {
        return new_etat;
    }

    public void setNew_etat(String new_etat) {
        this.new_etat = new_etat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}