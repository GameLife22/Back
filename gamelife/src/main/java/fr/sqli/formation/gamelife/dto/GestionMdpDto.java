package fr.sqli.formation.gamelife.dto;

public class GestionMdpDto {

    public GestionMdpDto() {
    }

    public GestionMdpDto(Integer id, String new_mdp, String old_mdp) {
        this.id = id;
        this.new_mdp = new_mdp;
        this.old_mdp = old_mdp;
    }

    private Integer id;

    private String new_mdp;

    private  String old_mdp;

    public void setNew_mdp(String new_mdp) {
        this.new_mdp = new_mdp;
    }

    public String getOld_mdp() {
        return old_mdp;
    }

    public void setOld_mdp(String old_mdp) {
        this.old_mdp = old_mdp;
    }

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