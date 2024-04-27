package fr.sqli.formation.gamelife.dto.response;

import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a response object for a game, containing the game's ID, name, description,
 * genres, platforms, and a list of images associated with the game.
 */
public record GameResponse(
        UUID id,
        String name,
        String description,
        Set<Genre> genres,
        Set<Platform> platforms,
        List<String> images
) {}