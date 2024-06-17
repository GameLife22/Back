package fr.sqli.formation.gamelife.dto;

public class Registration {
    private String email;

    private Integer etat;

    private String mdp;

    private String nom;

    private int num_rue;

    private String num_siret;

    private String prenom;

    private String rue;

    private String ville;

    private String role;

    private  int code_postal;
    public Registration() {
    }
    public Registration(String email, Integer etat, String mdp, String nom, int num_rue, String num_siret, String prenom, String role, String rue, String ville, int code_postal) {
        this.email = email;
        this.etat = etat;
        this.mdp = mdp;
        this.nom = nom;
        this.num_rue = num_rue;
        this.num_siret = num_siret;
        this.role = role;
        this.prenom = prenom;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String pEmail) {
        email = pEmail;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer pEtat) {
        etat = pEtat;
    }

    public String getPassword() {
        return mdp;
    }

    public void setPassword(String pMdp) {
        mdp = pMdp;
    }

    public String getLastName() {
        return nom;
    }

    public void setLastName(String pNom) {
        nom = pNom;
    }

    public int getNum_rue() {
        return num_rue;
    }

    public void setNum_rue(int pNum_rue) {
        num_rue = pNum_rue;
    }

    public String getNum_siret() {
        return num_siret;
    }

    public void setNum_siret(String pNum_siret) {
        num_siret = pNum_siret;
    }

    public String getFirstName() {
        return prenom;
    }

    public void setFirstName(String pPrenom) {
        prenom = pPrenom;
    }

    public String getStreet() {
        return rue;
    }

    public void setStreet(String pRue) {
        rue = pRue;
    }

    public String getCity() {
        return ville;
    }

    public void setCity(String pVille) {
        ville = pVille;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String pRole) {
        role = pRole;
    }

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int pCode_postal) {
        code_postal = pCode_postal;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Registration{");
        sb.append("email='").append(email).append('\'');
        sb.append(", etat=").append(etat);
        sb.append(", mdp='").append(mdp).append('\'');
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", num_rue=").append(num_rue);
        sb.append(", num_siret='").append(num_siret).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append(", code_postal=").append(code_postal);
        sb.append('}');
        return sb.toString();
    }
}
