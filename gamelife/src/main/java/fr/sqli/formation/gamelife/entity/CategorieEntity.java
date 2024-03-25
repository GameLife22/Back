package fr.sqli.formation.gamelife.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "glcategorie", schema = "gamelife")
public class CategorieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "libelle", nullable = false, length = 25)
    private String libelle;

    @Column(name = "etat", nullable = false)
    private boolean etat;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }

    public boolean getEtat() {
        return etat;
    }

    public void setEtat(boolean pEtat) {
        etat = pEtat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CategorieEntity{");
        sb.append("id=").append(id);
        sb.append(", libelle='").append(libelle).append('\'');
        sb.append(", etat=").append(etat);
        sb.append('}');
        return sb.toString();
    }
}