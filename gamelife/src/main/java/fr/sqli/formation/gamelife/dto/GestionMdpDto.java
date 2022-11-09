package fr.sqli.formation.gamelife.dto;

public class GestionMdpDto {
    private String new_mdp;

    private String old_mdp;

    private String email;

    public String getOld_mdp() {
        return old_mdp;
    }

    public void setOld_mdp(String old_mdp) {
        this.old_mdp = old_mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNew_mdp() {
        return new_mdp;
    }

    public void setNew_mdp(String new_mdp) {
        this.new_mdp = new_mdp;
    }
}
