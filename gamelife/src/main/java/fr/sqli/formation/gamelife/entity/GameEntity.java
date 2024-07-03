package fr.sqli.formation.gamelife.entity;

import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Entity class representing a game in the GameLife system.
 * Contains information such as ID, name, description, genres, platforms, and images.
 * Utilizes JPA annotations for mapping to the database.
 */
@Entity
@Table(name = "glgame", schema = "gamelife")
@NamedStoredProcedureQuery(
        name = "count_games",
        procedureName = "count_games",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "p_count_games")
        }
)
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @ElementCollection(targetClass = Genre.class)
    @JoinTable(name = "glgenre", joinColumns = @JoinColumn(name = "game_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Set<Genre> genres;

    @ElementCollection(targetClass = Platform.class)
    @JoinTable(name = "glplatform", joinColumns = @JoinColumn(name = "game_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private Set<Platform> platforms;

    @ElementCollection
    @CollectionTable(name = "glimage", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> pGenres) {
        genres = pGenres;
    }

    public Set<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<Platform> pPlatforms) {
        platforms = pPlatforms;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> pImages) {
        images = pImages;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GameEntity{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", genres=").append(genres);
        sb.append(", platforms=").append(platforms);
        sb.append(", images=").append(images);
        sb.append('}');
        return sb.toString();
    }
}