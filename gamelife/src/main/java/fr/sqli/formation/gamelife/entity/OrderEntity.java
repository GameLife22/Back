package fr.sqli.formation.gamelife.entity;

import fr.sqli.formation.gamelife.enumeration.OrderStatus;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "glcommande", schema = "gamelife")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<ItemOrderEntity> itemsCommande;

    @Column(name = "etat", nullable = false, length = 80)
    @Enumerated(EnumType.STRING)
    private OrderStatus etat;

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

    public OrderEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUtilisateur() {
        return user;
    }

    public void setUtilisateur(UserEntity pUtilisateur) {
        user = pUtilisateur;
    }

    public List<ItemOrderEntity> getItemsCommande() {
        return itemsCommande;
    }

    public void setItemsCommande(List<ItemOrderEntity> pItemsCommande) {
        this.itemsCommande = pItemsCommande;
    }

    public OrderStatus getEtat() {
        return etat;
    }

    public void setEtat(OrderStatus pEtat) {
        etat = pEtat;
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
    public boolean equals(Object pObject) {
        if (this == pObject) return true;
        if (pObject == null || getClass() != pObject.getClass()) return false;
        OrderEntity that = (OrderEntity) pObject;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(itemsCommande, that.itemsCommande) && etat == that.etat && Objects.equals(numRueLivraison, that.numRueLivraison) && Objects.equals(rueLivraison, that.rueLivraison) && Objects.equals(villeLivraison, that.villeLivraison) && Objects.equals(codePostalLivraison, that.codePostalLivraison) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, itemsCommande, etat, numRueLivraison, rueLivraison, villeLivraison, codePostalLivraison, date);
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