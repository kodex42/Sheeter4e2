package com.sheeter.azuris.sheeter4e.Modules;

public enum RaceName {
    DRAGONBORN,
    DWARF,
    ELADRIN,
    ELF,
    HALF_ELF,
    HALFLING,
    HUMAN,
    TIEFLING,;

    public static String getRaw(RaceName raceName) {
        String race = "N/A";
        if (raceName != null) {
            switch (raceName) {
                case DRAGONBORN:
                    race = "Dragonborn";
                    break;
                case DWARF:
                    race = "Dwarf";
                    break;
                case ELADRIN:
                    race = "Eladrin";
                    break;
                case ELF:
                    race = "Elf";
                    break;
                case HALF_ELF:
                    race = "Half-Elf";
                    break;
                case HALFLING:
                    race = "Halfling";
                    break;
                case HUMAN:
                    race = "Human";
                    break;
                case TIEFLING:
                    race = "Tiefling";
                    break;
            }
        }
        return race;
    }

    public static RaceName fromString(String race) {
        RaceName raceName = null;
        if (race != null) {
            switch (race) {
                case "Dragonborn":
                    raceName = DRAGONBORN;
                    break;
                case "Dwarf":
                    raceName = DWARF;
                    break;
                case "Eladrin":
                    raceName = ELADRIN;
                    break;
                case "Elf":
                    raceName = ELF;
                    break;
                case "Half-Elf":
                    raceName = HALF_ELF;
                    break;
                case "Halfling":
                    raceName = HALFLING;
                    break;
                case "Human":
                    raceName = HUMAN;
                    break;
                case "Tiefling":
                    raceName = TIEFLING;
                    break;
            }
        }

        return raceName;
    }

    public static String[] getArray() {
        return new String[]{
                getRaw(DRAGONBORN),
                getRaw(DWARF),
                getRaw(ELADRIN),
                getRaw(ELF),
                getRaw(HALF_ELF),
                getRaw(HALFLING),
                getRaw(HUMAN),
                getRaw(TIEFLING)
        };
    }
}
