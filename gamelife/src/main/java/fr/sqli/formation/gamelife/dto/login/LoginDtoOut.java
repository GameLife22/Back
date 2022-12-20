package fr.sqli.formation.gamelife.dto.login;

public class LoginDtoOut {
    private Integer id;
    private String email;

    private Integer etat;

    private String nom;

    private int num_rue;

    private String num_siren;

    private String prenom;

    private String rue;

    private String ville;

    private  int code_postal;

    public LoginDtoOut() {
    }

    public LoginDtoOut(Integer id,String email, Integer etat, String nom, int num_rue, String num_siren, String prenom, String rue, String ville, int code_postal) {
        this.id = id;
        this.email = email;
        this.etat = etat;
        this.nom = nom;
        this.num_rue = num_rue;
        this.num_siren = num_siren;
        this.prenom = prenom;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNum_rue() {
        return num_rue;
    }

    public void setNum_rue(int num_rue) {
        this.num_rue = num_rue;
    }

    public String getNum_siren() {
        return num_siren;
    }

    public void setNum_siren(String num_siren) {
        this.num_siren = num_siren;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
    }
}
