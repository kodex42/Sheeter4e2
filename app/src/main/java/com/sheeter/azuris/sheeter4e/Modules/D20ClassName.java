package com.sheeter.azuris.sheeter4e.Modules;

public enum D20ClassName {
    ARDENT,
    ARTIFICER,
    AVENGER,
    BARBARIAN,
    BARD,
    BATTLEMIND,
    CLERIC,
    DRUID,
    FIGHTER,
    INVOKER,
    MONK,
    PALADIN,
    PSION,
    RANGER,
    ROGUE,
    RUNEPRIEST,
    SEEKER,
    SHAMAN,
    SORCERER,
    WARDEN,
    WARLOCK,
    WARLORD,
    WIZARD,;

    public static String getRaw(D20ClassName className) {
        String name = "N/A";
        if (className != null) {
            switch (className) {
                case ARDENT:
                    name = "Ardent";
                    break;
                case ARTIFICER:
                    name = "Artificer";
                    break;
                case AVENGER:
                    name = "Avenger";
                    break;
                case BARBARIAN:
                    name = "Barbarian";
                    break;
                case BARD:
                    name = "Bard";
                    break;
                case BATTLEMIND:
                    name = "Battlemind";
                    break;
                case CLERIC:
                    name = "Cleric";
                    break;
                case DRUID:
                    name = "Druid";
                    break;
                case FIGHTER:
                    name = "Fighter";
                    break;
                case INVOKER:
                    name = "Invoker";
                    break;
                case MONK:
                    name = "Monk";
                    break;
                case PALADIN:
                    name = "Paladin";
                    break;
                case PSION:
                    name = "Psion";
                    break;
                case RANGER:
                    name = "Ranger";
                    break;
                case ROGUE:
                    name = "Rogue";
                    break;
                case RUNEPRIEST:
                    name = "Runepriest";
                    break;
                case SEEKER:
                    name = "Seeker";
                    break;
                case SHAMAN:
                    name = "Shaman";
                    break;
                case SORCERER:
                    name = "Sorcerer";
                    break;
                case WARDEN:
                    name = "Warden";
                    break;
                case WARLOCK:
                    name = "Warlock";
                    break;
                case WARLORD:
                    name = "Warlord";
                    break;
                case WIZARD:
                    name = "Wizard";
                    break;
            }
        }
        return name;
    }

    public static D20ClassName fromString(String name) {
        D20ClassName className = null;
        if (name != null) {
            switch (name) {
                case "Ardent":
                    className = ARDENT;
                    break;
                case "Artificer":
                    className = ARTIFICER;
                    break;
                case "Avenger":
                    className = AVENGER;
                    break;
                case "Barbarian":
                    className = BARBARIAN;
                    break;
                case "Bard":
                    className = BARD;
                    break;
                case "Battlemind":
                    className = BATTLEMIND;
                    break;
                case "Cleric":
                    className = CLERIC;
                    break;
                case "Druid":
                    className = DRUID;
                    break;
                case "Fighter":
                    className = FIGHTER;
                    break;
                case "Invoker":
                    className = INVOKER;
                    break;
                case "Monk":
                    className = MONK;
                    break;
                case "Paladin":
                    className = PALADIN;
                    break;
                case "Psion":
                    className = PSION;
                    break;
                case "Ranger":
                    className = RANGER;
                    break;
                case "Rogue":
                    className = ROGUE;
                    break;
                case "Runepriest":
                    className = RUNEPRIEST;
                    break;
                case "Seeker":
                    className = SEEKER;
                    break;
                case "Shaman":
                    className = SHAMAN;
                    break;
                case "Sorcerer":
                    className = SORCERER;
                    break;
                case "Warden":
                    className = WARDEN;
                    break;
                case "Warlock":
                    className = WARLOCK;
                    break;
                case "Warlord":
                    className = WARLORD;
                    break;
                case "Wizard":
                    className = WIZARD;
                    break;
            }
        }

        return className;
    }

    public static String[] getArray() {
        return new String[]{
                getRaw(ARDENT),
                getRaw(ARTIFICER),
                getRaw(AVENGER),
                getRaw(BARBARIAN),
                getRaw(BARD),
                getRaw(BATTLEMIND),
                getRaw(CLERIC),
                getRaw(DRUID),
                getRaw(FIGHTER),
                getRaw(INVOKER),
                getRaw(MONK),
                getRaw(PALADIN),
                getRaw(PSION),
                getRaw(RANGER),
                getRaw(ROGUE),
                getRaw(RUNEPRIEST),
                getRaw(SEEKER),
                getRaw(SHAMAN),
                getRaw(SORCERER),
                getRaw(WARDEN),
                getRaw(WARLOCK),
                getRaw(WARLORD),
                getRaw(WIZARD)
        };
    }
}
