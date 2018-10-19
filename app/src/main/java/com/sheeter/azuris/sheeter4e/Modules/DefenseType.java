package com.sheeter.azuris.sheeter4e.Modules;

public enum DefenseType {
    ARMOR_CLASS,
    FORTITUDE,
    REFLEX,
    WILL,;

    public static String getRaw(DefenseType defenseType) {
        String defense = "N/A";
        if (defenseType != null) {
            switch (defenseType) {
                case ARMOR_CLASS:
                    defense = "Armor Class";
                    break;
                case FORTITUDE:
                    defense = "Fortitude";
                    break;
                case REFLEX:
                    defense = "Reflex";
                    break;
                case WILL:
                    defense = "Will";
                    break;
            }
        }
        return defense;
    }
}
