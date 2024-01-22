package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "glcommande", schema = "gamelife")
public class CommandeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private UtilisateurEntity idUtilisateur;

    @Column(name = "etat", nullable = false, length = 80)
    private String etat;

    @Column(name = "num_rue_livraison", nullable = false)
    private Integer numRueLivraison;

    @Column(name = "rue_livraison", nullable = false)
    private String rueLivraison;

    @Column(name = "ville_livraison", nullable = false, length = 80)
    private String villeLivraison;

    @Column(name = "code_postal_livraison")
    private Integer codePostalLivraison;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public CommandeEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UtilisateurEntity getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(UtilisateurEntity idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Integer getNumRueLivraison() {
        return numRueLivraison;
    }

    public void setNumRueLivraison(Integer numRueLivraison) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommandeEntity{");
        sb.append("id=").append(id);
        sb.append(", idUtilisateur=").append(idUtilisateur);
        sb.append(", etat='").append(etat).append('\'');
        sb.append(", numRueLivraison=").append(numRueLivraison);
        sb.append(", rueLivraison='").append(rueLivraison).append('\'');
        sb.append(", villeLivraison='").append(villeLivraison).append('\'');
        sb.append(", codePostalLivraison=").append(codePostalLivraison);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}