package fr.sqli.formation.gamelife.dto.commande;

import fr.sqli.formation.gamelife.enumeration.EtatCommande;

import java.io.Serial;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CommandeRequete {
    private UUID id;
    private UUID idUtilisateur;
    private EtatCommande etat;
    private Integer numRueLivraison;
    private String rueLivraison;
    private String villeLivraison;
    private Integer codePostalLivraison;
    private LocalDate date;
    private List<ItemCommandeRequete> itemsCommande;

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public List<ItemCommandeRequete> getItemsCommande() {
        return itemsCommande;
    }

    public void setItemsCommande(List<ItemCommandeRequete> itemsCommande) {
        this.itemsCommande = itemsCommande;
    }

    public UUID getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(UUID pIdUtilisateur) {
        idUtilisateur = pIdUtilisateur;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
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
        final StringBuffer sb = new StringBuffer("CommandeDtoIn{");
        sb.append("id=").append(id);
        sb.append(", idUtilisateur=").append(idUtilisateur);
        sb.append(", etat=").append(etat);
        sb.append(", numRueLivraison=").append(numRueLivraison);
        sb.append(", rueLivraison='").append(rueLivraison).append('\'');
        sb.append(", villeLivraison='").append(villeLivraison).append('\'');
        sb.append(", codePostalLivraison=").append(codePostalLivraison);
        sb.append(", date=").append(date);
        sb.append(", itemsCommande=").append(itemsCommande);
        sb.append('}');
        return sb.toString();
    }
}
