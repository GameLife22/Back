package fr.sqli.formation.gamelife.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "glplateforme", schema = "gamelife")
public class PlateformeEntity {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @Size(max = 25)
    @NotNull
    @Column(name = "nom", nullable = false, length = 25)
    private String nom;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PlateformeEntity{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append('}');
        return sb.toString();
    }
}