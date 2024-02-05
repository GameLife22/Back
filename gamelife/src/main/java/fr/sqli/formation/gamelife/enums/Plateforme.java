package fr.sqli.formation.gamelife.enums;

import fr.sqli.formation.gamelife.ex.ParameterException;

public enum Plateforme {
    PC("pc"),
    PLAYSTATION5("playstation 5"),
    PLAYSTATION4("playstation 4"),
    PLAYSTATION3("playstation 3"),
    PLAYSTATION2("playstation 2"),
    PLAYSTATION("playstation"),
    PSVITA("ps vita"),
    PSP("psp"),
    XBOXONE("xbox one"),
    XBOXSERIESS("xbox series s"),
    XBOXSERIESX("xbox series x"),
    XBOX360("xbox 360"),
    XBOX("xbox"),
    NINTENDOSWITCH("nintendo switch"),
    NINTENDO3DS("nintendo 3ds"),
    NINTENDODS("nintendo ds"),
    NINTENDODSI("nintendo dsi"),
    NINTENDO64("nintendo 64"),
    WIIU("wii u"),
    WII("wii"),
    CAMECUBE("camecube"),
    GAMEBOYADVANCE("gameboy advance"),
    GAMEBOYCOLOR("gameboy color"),
    GAMEBOY("gameboy"),
    SNES("snes"),
    NES("nes"),
    IOS("ios"),
    ANDROID("android"),
    MACOS("macos"),
    CLASSICMACINTOSH("classic macintosh"),
    APPLEII("apple ii"),
    LINUX("linux"),
    COMODOREAMIGA("comodore amiga"),
    ATARI7800("atari 7800"),
    ATARI5200("atari 5200"),
    ATARI2600("atari 2600"),
    ATARIFLASHBACK("atari flashback"),
    ATARI8BIT("atari 8bit"),
    ATARIST("atarist"),
    ATARILYNX("atari lynx"),
    ATARIXEGS("atari xegs"),
    GENESIS("genesis"),
    SEGASATURN("sega saturn"),
    SEGACD("sega cd"),
    SEGA32X("sega 32x"),
    SEGAMASTERSYSTEM("sega master system"),
    DREAMCAST("dreamcast"),
    _3DO("3do"),
    JAGUAR("jaguar"),
    GAMEGEAR("gamegear"),
    NEOGEO("neogeo"),
    WEB("web");

    private final String libelle;

    Plateforme(String pLibelle) {
        this.libelle = pLibelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public static String fromString(String pLibelle) throws ParameterException {
        if (Character.isDigit(pLibelle.charAt(0))) {
            pLibelle = "_" + pLibelle;
        }

        for (Plateforme plateforme : Plateforme.values()) {
            if (plateforme.getLibelle().equalsIgnoreCase(pLibelle)) {
                return pLibelle;
            }
        }
        throw new ParameterException("Plateforme invalide : " + pLibelle);
    }
}