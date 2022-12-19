package fr.sqli.formation.gamelife.dto;

public class SiretDto {
    private String siret;

    public SiretDto() {
    }

    public SiretDto(String siret) {
        this.siret = siret;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }
}
