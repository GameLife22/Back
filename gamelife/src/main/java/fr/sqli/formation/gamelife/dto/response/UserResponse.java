package fr.sqli.formation.gamelife.dto.response;

public class UserResponse {
    private String nom;
    private String prenom;
    private String email;
    private Integer num_rue;
    private String rue;
    private String ville;
    private Integer code_postal;
    private String num_siret;

    public String getNum_siret() {
        return num_siret;
    }

    public void setNum_siret(String num_siret) {
        this.num_siret = num_siret;
    }

    public String getLastName() {
        return nom;
    }

    public void setLastName(String nom) {
        this.nom = nom;
    }

    public String getFirstName() {
        return prenom;
    }

    public void setFirstName(String prenom) {
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

    public String getStreet() {
        return rue;
    }

    public void setStreet(String rue) {
        this.rue = rue;
    }

    public String getCity() {
        return ville;
    }

    public void setCity(String ville) {
        this.ville = ville;
    }

    public Integer getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(Integer code_postal) {
        this.code_postal = code_postal;
    }
}
