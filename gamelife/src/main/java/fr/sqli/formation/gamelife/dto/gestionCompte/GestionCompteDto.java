package fr.sqli.formation.gamelife.dto.gestionCompte;

public class GestionCompteDto {

    public GestionCompteDto() {
    }

    public GestionCompteDto(Integer id, String nom, String prenom, String email, Integer numRue, String rue, String ville, Integer codePostal, String numSiren) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.numRue = numRue;
        this.codePostal = codePostal;
        this.numSiren = numSiren;
        this.prenom = prenom;
        this.rue = rue;
        this.ville = ville;
    }

    private  Integer id;
    private String email;

    private String nom;

    private Integer numRue;

    private Integer codePostal;

    private String numSiren;

    private String prenom;

    private String rue;

    private String ville;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodePostal() {
        return codePostal;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNumRue() {
        return numRue;
    }

    public void setNumRue(Integer numRue) {
        this.numRue = numRue;
    }

    public String getNumSiren() {
        return numSiren;
    }

    public void setNumSiren(String numSiren) {
        this.numSiren = numSiren;
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
}