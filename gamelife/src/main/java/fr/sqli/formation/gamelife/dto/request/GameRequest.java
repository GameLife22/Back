package fr.sqli.formation.gamelife.dto.request;

import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;

import fr.sqli.formation.gamelife.utility.constraint.IInValidImagesURLsConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a data transfer object for creating or updating a game.
 * Contains the game's ID, name, description, genres, platforms, and images.
 */
public record GameRequest(
        UUID id,
        @Size(max = 50, message = "Name must not exceed 50 characters")
        @NotBlank(message = "Name is required") String name,
        @Size(max = 5000, message = "Description must not exceed 5000 characters")
        @NotBlank(message = "Description is required") String description,
        @NotEmpty(message = "Genres must not be empty") Set<Genre> genres,
        @NotEmpty(message = "Platforms must not be empty") Set<Platform> platforms,
        @IInValidImagesURLsConstraint List<String> images
) {}