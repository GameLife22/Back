package fr.sqli.formation.gamelife.dto.gestionCompte;

import java.util.UUID;

public class GestionMdpDto {

    public GestionMdpDto() {
    }

    public GestionMdpDto(UUID id, String new_mdp, String old_mdp) {
        this.id = id;
        this.new_mdp = new_mdp;
        this.old_mdp = old_mdp;
    }

    private UUID id;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public String getNew_mdp() {
        return new_mdp;
    }

}