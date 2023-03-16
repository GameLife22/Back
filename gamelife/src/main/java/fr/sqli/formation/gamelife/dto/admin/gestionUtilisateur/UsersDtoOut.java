package fr.sqli.formation.gamelife.dto.admin.gestionUtilisateur;

public class UsersDtoOut {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private Integer num_rue;
    private String rue;
    private String ville;
    private Integer code_postal;

    private String role;
    private String num_siren;
    private Integer etat_compte;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNum_rue() {
        return num_rue;
    }

    public void setNum_rue(Integer num_rue) {
        this.num_rue = num_rue;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(Integer code_postal) {
        this.code_postal = code_postal;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNum_siren() {
        return num_siren;
    }

    public void setNum_siren(String num_siren) {
        this.num_siren = num_siren;
    }

    public Integer getEtat_compte() {
        return etat_compte;
    }

    public void setEtat_compte(Integer etat_compte) {
        this.etat_compte = etat_compte;
    }
}
