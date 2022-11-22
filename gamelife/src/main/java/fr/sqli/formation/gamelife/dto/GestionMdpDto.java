package fr.sqli.formation.gamelife.dto;

public class GestionMdpDto {

    public GestionMdpDto() {
    }

    public GestionMdpDto(Integer id, String new_mdp) {
        this.id = id;
        this.new_mdp = new_mdp;
    }

    private Integer id;

    private String new_mdp;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNew_mdp() {
        return new_mdp;
    }

}