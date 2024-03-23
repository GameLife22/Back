package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "glproduit", schema = "gamelife")
public class ProduitEntity {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "description", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_categorie", nullable = false)
    private CategorieEntity idCategorie;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_plateforme", nullable = false)
    private PlateformeEntity idPlateforme;

    @Column(name = "etat")
    private Boolean etat;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategorieEntity getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(CategorieEntity idCategorie) {
        this.idCategorie = idCategorie;
    }

    public PlateformeEntity getIdPlateforme() {
        return idPlateforme;
    }

    public void setIdPlateforme(PlateformeEntity idPlateforme) {
        this.idPlateforme = idPlateforme;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitEntity{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", idCategorie=").append(idCategorie);
        sb.append(", idPlateforme=").append(idPlateforme);
        sb.append(", etat=").append(etat);
        sb.append('}');
        return sb.toString();
    }
}