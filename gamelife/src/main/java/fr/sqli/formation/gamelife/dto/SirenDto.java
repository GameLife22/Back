package fr.sqli.formation.gamelife.dto;

public class SirenDto {
    private String siren;

    public SirenDto() {
    }

    public SirenDto(String siren) {
        this.siren = siren;
    }

    public String getSiret() {
        return siren;
    }

    public void setSiret(String siren) {
        this.siren = siren;
    }
}
