package fr.sqli.formation.gamelife.dto.request;

import fr.sqli.formation.gamelife.enumeration.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class OrderRequest {
    private UUID id;
    private UUID idUtilisateur;
    private OrderStatus etat;
    private Integer numRueLivraison;
    private String rueLivraison;
    private String villeLivraison;
    private Integer codePostalLivraison;
    private LocalDate date;

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    private List<ItemOrderRequest> itemsCommande;

    public List<ItemOrderRequest> getItemsCommande() {
        return itemsCommande;
    }

    public void setItemsCommande(List<ItemOrderRequest> itemsCommande) {
        this.itemsCommande = itemsCommande;
    }

    public UUID getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(UUID pIdUtilisateur) {
        idUtilisateur = pIdUtilisateur;
    }

    public OrderStatus getEtat() {
        return etat;
    }

    public void setEtat(OrderStatus etat) {
        this.etat = etat;
    }

    public Integer getStreetNumberLivraison() {
        return numRueLivraison;
    }

    public void setStreetNumberLivraison(Integer pNumRueLivraison) {
        numRueLivraison = pNumRueLivraison;
    }

    public String getStreetLivraison() {
        return rueLivraison;
    }

    public void setStreetLivraison(String pRueLivraison) {
        rueLivraison = pRueLivraison;
    }

    public String getCityLivraison() {
        return villeLivraison;
    }

    public void setCityLivraison(String pVilleLivraison) {
        villeLivraison = pVilleLivraison;
    }

    public Integer getZipCodeLivraison() {
        return codePostalLivraison;
    }

    public void setZipCodeLivraison(Integer pCodePostalLivraison) {
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
