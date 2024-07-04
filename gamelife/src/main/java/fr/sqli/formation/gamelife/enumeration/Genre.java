package fr.sqli.formation.gamelife.enumeration;

import com.fasterxml.jackson.annotation.*;
import fr.sqli.formation.gamelife.exception.ParameterException;

/**
 * Enum representing different genres of games.
 * Each genre has a corresponding name.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public enum Genre {
    ACTION("Action"),
    INDIE("Indie"),
    ADVENTURE("Adventure"),
    RPG("RPG"),
    STRATEGY("Strategy"),
    SHOOTER("Shooter"),
    CASUAL("Casual"),
    SIMULATION("Simulation"),
    PUZZLE("Puzzle"),
    ARCADE("Arcade"),
    PLATFORMER("Platformer"),
    RACING("Racing"),
    MASSIVELY_MULTIPLAYER("Massively Multiplayer"),
    SPORTS("Sports"),
    FIGHTING("Fighting"),
    FAMILY("Family"),
    BOARD_GAMES("Board Games"),
    EDUCATIONAL("Educational"),
    CARD("Card");

    private final String name;

    Genre(String pName) {
        this.name = pName;
    }

    @JsonValue // serialization
    public String getName() {
        return this.name;
    }

    /**
     * Finds and returns the Genre enum value corresponding to the given genre name.
     *
     * @param pName the name of the genre to search for
     * @return the Genre enum value matching the given name
     * @throws ParameterException if the provided genre name is not valid
     */
    @JsonCreator // deserialization
    public static Genre findGenreByName(String pName) throws ParameterException {
        for (Genre genre : Genre.values()) {
            if (genre.getName().equalsIgnoreCase(pName)) {
                return genre;
            }
        }
        throw new ParameterException("Invalid Genre: " + pName);
    }
}