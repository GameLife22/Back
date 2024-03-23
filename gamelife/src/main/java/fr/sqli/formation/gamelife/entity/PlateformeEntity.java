package fr.sqli.formation.gamelife.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "glplateforme", schema = "gamelife")
public class PlateformeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @Column(name = "libelle", nullable = false, length = 25)
    private String libelle;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PlateformeEntity{");
        sb.append("id=").append(id);
        sb.append(", libelle='").append(libelle).append('\'');
        sb.append('}');
        return sb.toString();
    }
}