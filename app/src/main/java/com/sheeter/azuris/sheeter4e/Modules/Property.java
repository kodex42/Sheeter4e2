package com.sheeter.azuris.sheeter4e.Modules;

public enum Property {
    ONE_HANDED,
    TWO_HANDED,
    OFF_HAND,
    LIGHT_THROWN,
    HEAVY_THROWN,
    SMALL,
    VERSATILE,
    BRUTAL,
    MOUNTED,
    HIGH_CRIT,
    REACH,
    DEFENSIVE,
    DOUBLE_WEAPON,
    STOUT,
    LOAD_FREE,
    LOAD_MINOR,
    REQUIRES_MAGAZINE,;

    public static String getRaw(Property property) {
        String prop = "N/A";
        if (property != null) {
            switch (property) {
                case ONE_HANDED:
                    prop = "One-Handed";
                    break;
                case TWO_HANDED:
                    prop = "Two-Handed";
                    break;
                case OFF_HAND:
                    prop = "Off-Hand";
                    break;
                case LIGHT_THROWN:
                    prop = "Light Thrown";
                    break;
                case HEAVY_THROWN:
                    prop = "Heavy Thrown";
                    break;
                case SMALL:
                    prop = "Small";
                    break;
                case VERSATILE:
                    prop = "Versatile";
                    break;
                case BRUTAL:
                    prop = "Brutal";
                    break;
                case MOUNTED:
                    prop = "Mounted";
                    break;
                case HIGH_CRIT:
                    prop = "High Crit";
                    break;
                case REACH:
                    prop = "Reach";
                    break;
                case DEFENSIVE:
                    prop = "Defensive";
                    break;
                case DOUBLE_WEAPON:
                    prop = "Double Weapon";
                    break;
                case STOUT:
                    prop = "Stout";
                    break;
                case LOAD_FREE:
                    prop = "Load Free";
                    break;
                case LOAD_MINOR:
                    prop = "Load Minor";
                    break;
                case REQUIRES_MAGAZINE:
                    prop = "Requires Magazine";
                    break;
            }
        }
        return prop;
    }
}
