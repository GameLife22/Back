package fr.sqli.formation.gamelife.enumeration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import fr.sqli.formation.gamelife.exception.ParameterException;

/**
 * Enum representing different gaming platforms with their names.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public enum Platform {
    PC("PC"),
    PLAYSTATION_5("PlayStation 5"),
    XBOX_ONE("Xbox One"),
    PLAYSTATION_4("PlayStation 4"),
    XBOX_SERIES_SX("Xbox Series S/X"),
    NINTENDO_SWITCH("Nintendo Switch"),
    IOS("iOS"),
    ANDROID("Android"),
    NINTENDO_3DS("Nintendo 3DS"),
    NINTENDO_DS("Nintendo DS"),
    NINTENDO_DSI("Nintendo DSi"),
    MACOS("macOS"),
    LINUX("Linux"),
    XBOX_360("Xbox 360"),
    XBOX("Xbox"),
    PLAYSTATION_3("PlayStation 3"),
    PLAYSTATION_2("PlayStation 2"),
    PLAYSTATION("PlayStation"),
    PS_VITA("PS Vita"),
    PSP("PSP"),
    WII_U("Wii U"),
    WII("Wii"),
    GAMECUBE("GameCube"),
    NINTENDO_64("Nintendo 64"),
    GAME_BOY_ADVANCE("Game Boy Advance"),
    GAME_BOY_COLOR("Game Boy Color"),
    GAME_BOY("Game Boy"),
    SNES("SNES"),
    NES("NES"),
    CLASSIC_MACINTOSH("Classic Macintosh"),
    APPLE_II("Apple II"),
    COMMODORE_AMIGA("Commodore / Amiga"),
    ATARI_7800("Atari 7800"),
    ATARI_5200("Atari 5200"),
    ATARI_2600("Atari 2600"),
    ATARI_FLASHBACK("Atari Flashback"),
    ATARI_8_BIT("Atari 8-bit"),
    ATARI_ST("Atari ST"),
    ATARI_LYNX("Atari Lynx"),
    ATARI_XEGS("Atari XEGS"),
    GENESIS("Genesis"),
    SEGA_SATURN("SEGA Saturn"),
    SEGA_CD("SEGA CD"),
    SEGA_32X("SEGA 32X"),
    SEGA_MASTER_SYSTEM("SEGA Master System"),
    DREAMCAST("Dreamcast"),
    THREE_DO("3DO"),
    JAGUAR("Jaguar"),
    GAME_GEAR("Game Gear"),
    NEO_GEO("Neo Geo"),
    WEB("Web");

    private final String name;

    Platform(String pName) {
        this.name = pName;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static Platform findPlatformByName(String pName) throws ParameterException {
        for (Platform platform : Platform.values()) {
            if (platform.getName().equalsIgnoreCase(pName)) {
                return platform;
            }
        }
        throw new ParameterException("Invalid Platform: " + pName);
    }
}