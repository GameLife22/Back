package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "glcommande", schema = "gamelife")
public class CommandeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private UtilisateurEntity utilisateur;

    @Size(max = 80)
    @NotNull
    @Column(name = "etat", nullable = false, length = 80)
    private String etat;

    @NotNull
    @Column(name = "num_rue_livraison", nullable = false)
    private Integer numRueLivraison;

    @Size(max = 255)
    @NotNull
    @Column(name = "rue_livraison", nullable = false)
    private String rueLivraison;

    @Size(max = 80)
    @NotNull
    @Column(name = "ville_livraison", nullable = false, length = 80)
    private String villeLivraison;

    @NotNull
    @Column(name = "code_postal_livraison", nullable = false)
    private Integer codePostalLivraison;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UtilisateurEntity getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurEntity pUtilisateur) {
        utilisateur = pUtilisateur;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String pEtat) {
        etat = pEtat;
    }

    public Integer getNumRueLivraison() {
        return numRueLivraison;
    }

    public void setNumRueLivraison(Integer pNumRueLivraison) {
        numRueLivraison = pNumRueLivraison;
    }

    public String getRueLivraison() {
        return rueLivraison;
    }

    public void setRueLivraison(String pRueLivraison) {
        rueLivraison = pRueLivraison;
    }

    public String getVilleLivraison() {
        return villeLivraison;
    }

    public void setVilleLivraison(String pVilleLivraison) {
        villeLivraison = pVilleLivraison;
    }

    public Integer getCodePostalLivraison() {
        return codePostalLivraison;
    }

    public void setCodePostalLivraison(Integer pCodePostalLivraison) {
        codePostalLivraison = pCodePostalLivraison;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate pDate) {
        date = pDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommandeEntity{");
        sb.append("id=").append(id);
        sb.append(", utilisateur=").append(utilisateur);
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