package fr.sqli.formation.gamelife.enumeration;

import com.fasterxml.jackson.annotation.*;

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
     * Retrieves a Genre based on the provided name.
     *
     * @param pName The name of the Genre to retrieve.
     * @return The Genre corresponding to the provided name.
     * @throws IllegalArgumentException if the provided name does not match any Genre.
     */
    @JsonCreator // deserialization
    public static Genre findGenreByName(String pName) {
        for (Genre genre : Genre.values()) {
            if (genre.getName().equalsIgnoreCase(pName)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid Genre: " + pName);
    }
}