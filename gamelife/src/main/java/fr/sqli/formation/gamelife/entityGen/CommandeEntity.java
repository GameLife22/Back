package fr.sqli.formation.gamelife.entityGen;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "commande", schema = "public", catalog = "gamelife")
public class CommandeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_utilisateur")
    private int idUtilisateur;
    @Basic
    @Column(name = "etat")
    private String etat;
    @Basic
    @Column(name = "num_rue_livraison")
    private int numRueLivraison;
    @Basic
    @Column(name = "rue_livraison")
    private String rueLivraison;
    @Basic
    @Column(name = "ville_livraison")
    private String villeLivraison;
    @Basic
    @Column(name = "code_postal_livraison")
    private Integer codePostalLivraison;
    @Basic
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id", nullable = false)
    private UtilisateurEntity utilisateurByIdUtilisateur;
    @OneToMany(mappedBy = "commandeByIdCommande")
    private Collection<ItemCommandeEntity> itemCommandesById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getNumRueLivraison() {
        return numRueLivraison;
    }

    public void setNumRueLivraison(int numRueLivraison) {
        this.numRueLivraison = numRueLivraison;
    }

    public String getRueLivraison() {
        return rueLivraison;
    }

    public void setRueLivraison(String rueLivraison) {
        this.rueLivraison = rueLivraison;
    }

    public String getVilleLivraison() {
        return villeLivraison;
    }

    public void setVilleLivraison(String villeLivraison) {
        this.villeLivraison = villeLivraison;
    }

    public Integer getCodePostalLivraison() {
        return codePostalLivraison;
    }

    public void setCodePostalLivraison(Integer codePostalLivraison) {
        this.codePostalLivraison = codePostalLivraison;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandeEntity that = (CommandeEntity) o;
        return id == that.id && idUtilisateur == that.idUtilisateur && numRueLivraison == that.numRueLivraison && Objects.equals(etat, that.etat) && Objects.equals(rueLivraison, that.rueLivraison) && Objects.equals(villeLivraison, that.villeLivraison) && Objects.equals(codePostalLivraison, that.codePostalLivraison) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUtilisateur, etat, numRueLivraison, rueLivraison, villeLivraison, codePostalLivraison, date);
    }

    public UtilisateurEntity getUtilisateurByIdUtilisateur() {
        return utilisateurByIdUtilisateur;
    }

    public void setUtilisateurByIdUtilisateur(UtilisateurEntity utilisateurByIdUtilisateur) {
        this.utilisateurByIdUtilisateur = utilisateurByIdUtilisateur;
    }

    public Collection<ItemCommandeEntity> getItemCommandesById() {
        return itemCommandesById;
    }

    public void setItemCommandesById(Collection<ItemCommandeEntity> itemCommandesById) {
        this.itemCommandesById = itemCommandesById;
    }
}
