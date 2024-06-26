package fr.sqli.formation.gamelife.dto.request;

import fr.sqli.formation.gamelife.enumeration.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class OrderRequest {
    private UUID id;

    @NotNull(message = "User ID is required")
    private UUID idUtilisateur;

    @NotNull(message = "Order status is required")
    private OrderStatus etat;

    @NotNull(message = "Street number is required")
    private Integer numRueLivraison;

    @NotBlank(message = "Street name is required")
    @Size(max = 255, message = "Street name must not exceed 255 characters")
    private String rueLivraison;

    @NotBlank(message = "City is required")
    @Size(max = 255, message = "City name must not exceed 255 characters")
    private String villeLivraison;

    @NotNull(message = "Postal code is required")
    private Integer codePostalLivraison;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotEmpty(message = "Items cannot be empty")
    private List<ItemOrderRequest> itemsCommande;

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

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
