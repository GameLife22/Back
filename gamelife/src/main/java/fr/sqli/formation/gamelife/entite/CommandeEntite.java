package fr.sqli.formation.gamelife.entite;

import fr.sqli.formation.gamelife.enumeration.EtatCommande;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "glcommande", schema = "gamelife")
public class CommandeEntite {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private UtilisateurEntite utilisateur;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<ItemCommandeEntite> itemsCommande;

    @Column(name = "etat", nullable = false, length = 80)
    @Enumerated(EnumType.STRING)
    private EtatCommande etat;

    @Column(name = "num_rue_livraison", nullable = false)
    private Integer numRueLivraison;

    @Column(name = "rue_livraison", nullable = false)
    private String rueLivraison;

    @Column(name = "ville_livraison", nullable = false, length = 80)
    private String villeLivraison;

    @Column(name = "code_postal_livraison", nullable = false)
    private Integer codePostalLivraison;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public CommandeEntite() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UtilisateurEntite getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurEntite pUtilisateur) {
        utilisateur = pUtilisateur;
    }

    public List<ItemCommandeEntite> getItemsCommande() {
        return itemsCommande;
    }

    public void setItemsCommande(List<ItemCommandeEntite> pItemsCommande) {
        this.itemsCommande = pItemsCommande;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande pEtat) {
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
    public boolean equals(Object pObject) {
        if (this == pObject) return true;
        if (pObject == null || getClass() != pObject.getClass()) return false;
        CommandeEntite that = (CommandeEntite) pObject;
        return Objects.equals(id, that.id) && Objects.equals(utilisateur, that.utilisateur) && Objects.equals(itemsCommande, that.itemsCommande) && etat == that.etat && Objects.equals(numRueLivraison, that.numRueLivraison) && Objects.equals(rueLivraison, that.rueLivraison) && Objects.equals(villeLivraison, that.villeLivraison) && Objects.equals(codePostalLivraison, that.codePostalLivraison) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utilisateur, itemsCommande, etat, numRueLivraison, rueLivraison, villeLivraison, codePostalLivraison, date);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommandeEntity{");
        sb.append("id=").append(id);
        sb.append(", etat=").append(etat);
        sb.append(", numRueLivraison=").append(numRueLivraison);
        sb.append(", rueLivraison='").append(rueLivraison).append('\'');
        sb.append(", villeLivraison='").append(villeLivraison).append('\'');
        sb.append(", codePostalLivraison=").append(codePostalLivraison);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}