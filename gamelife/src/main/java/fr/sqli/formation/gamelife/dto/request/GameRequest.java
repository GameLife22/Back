package fr.sqli.formation.gamelife.dto.request;

import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;

import fr.sqli.formation.gamelife.utility.constraint.IInValidImagesURLsConstraint;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a data transfer object for creating or updating a game.
 * Contains the game's ID, name, description, genres, platforms, and images.
 */
public class GameRequest {

        UUID id;

        @Size(max = 100, message = "Name must not exceed 100 characters")
        @NotBlank(message = "Name is required")
        String name;

        @Size(max = 5000, message = "Description must not exceed 5000 characters")
        @NotBlank(message = "Description is required")
        String description;

        @NotEmpty(message = "Genres must not be empty")
        Set<Genre> genres;

        @NotEmpty(message = "Platforms must not be empty")
        Set<Platform> platforms;

        @IInValidImagesURLsConstraint
        List<String> images;

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
                final StringBuffer sb = new StringBuffer("GameRequest{");
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