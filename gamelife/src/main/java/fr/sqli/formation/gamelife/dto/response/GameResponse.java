package fr.sqli.formation.gamelife.dto.response;

import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a response object for a game, containing the game's ID, name, description,
 * genres, platforms, and a list of images associated with the game.
 */
public class GameResponse {

    UUID id;
    String name;
    String description;
    Set<Genre> genres;
    Set<Platform> platforms;
    List<String> images;
    BigDecimal prix;


    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

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
        final StringBuffer sb = new StringBuffer("GameResponse{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", genres=").append(genres);
        sb.append(", platforms=").append(platforms);
        sb.append(", images=").append(images);
        sb.append(", prix=").append(prix);
        sb.append('}');
        return sb.toString();
    }
}