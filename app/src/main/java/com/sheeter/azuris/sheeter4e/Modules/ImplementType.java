package com.sheeter.azuris.sheeter4e.Modules;

public enum ImplementType {
    DAGGER,
    HOLY_SYMBOL,
    KI_FOCUS,
    ORB,
    ROD,
    STAFF,
    TOTEM,
    WAND,
    WEAPON;

    public static String getRaw(ImplementType implementType) {
        String implement = "N/A";
        if (implementType != null) {
            switch (implementType) {
                case DAGGER:
                    implement = "Dagger";
                    break;
                case HOLY_SYMBOL:
                    implement = "Holy Symbol";
                    break;
                case KI_FOCUS:
                    implement = "Ki Focus";
                    break;
                case ORB:
                    implement = "Orb";
                    break;
                case ROD:
                    implement = "Rod";
                    break;
                case STAFF:
                    implement = "Staff";
                    break;
                case TOTEM:
                    implement = "Totem";
                    break;
                case WAND:
                    implement = "Wand";
                    break;
                case WEAPON:
                    implement = "Weapon";
                    break;
            }
        }
        return implement;
    }
}
