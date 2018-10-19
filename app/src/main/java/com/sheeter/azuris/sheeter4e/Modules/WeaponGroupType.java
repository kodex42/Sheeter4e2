package com.sheeter.azuris.sheeter4e.Modules;

public enum WeaponGroupType {
    AXE,
    BLOWGUN,
    BOLA,
    BOW,
    CLUB,
    CROSSBOW,
    DAGGER,
    FLAIL,
    GARROTE,
    HAMMER,
    HAND_CROSSBOW,
    HEAVY_BLADE,
    LIGHT_BLADE,
    LONGSPEAR,
    LONGSWORD,
    MACE,
    MONK_UNARMED_STRIKE,
    PICK,
    POLEARM,
    QUARTERSTAFF,
    SCIMITAR,
    SHORTBOW,
    SHORTSWORD,
    SHURIKEN,
    SLING,
    SPEAR,
    STAFF,
    UNARMED,;

    public static String getRaw(WeaponGroupType weaponGroupType) {
        String weaponGroup = "N/A";
        if (weaponGroupType != null) {
            switch (weaponGroupType) {
                case AXE:
                    weaponGroup = "Axe";
                    break;
                case BLOWGUN:
                    weaponGroup = "Blowgun";
                    break;
                case BOLA:
                    weaponGroup = "Bola";
                    break;
                case BOW:
                    weaponGroup = "Bow";
                    break;
                case CLUB:
                    weaponGroup = "Club";
                    break;
                case CROSSBOW:
                    weaponGroup = "Crossbow";
                    break;
                case DAGGER:
                    weaponGroup = "Dagger";
                    break;
                case FLAIL:
                    weaponGroup = "Flail";
                    break;
                case GARROTE:
                    weaponGroup = "Garrote";
                    break;
                case HAMMER:
                    weaponGroup = "Hammer";
                    break;
                case HAND_CROSSBOW:
                    weaponGroup = "Hand Crossbow";
                    break;
                case HEAVY_BLADE:
                    weaponGroup = "Heavy Blade";
                    break;
                case LIGHT_BLADE:
                    weaponGroup = "Light Blade";
                    break;
                case LONGSWORD:
                    weaponGroup = "Longsword";
                    break;
                case LONGSPEAR:
                    weaponGroup = "Longspear";
                    break;
                case MACE:
                    weaponGroup = "Mace";
                    break;
                case MONK_UNARMED_STRIKE:
                    weaponGroup = "Monk Unarmed Strike";
                    break;
                case PICK:
                    weaponGroup = "Pick";
                    break;
                case POLEARM:
                    weaponGroup = "Polearm";
                    break;
                case QUARTERSTAFF:
                    weaponGroup = "Quarterstaff";
                    break;
                case SCIMITAR:
                    weaponGroup = "Scimitar";
                    break;
                case SHORTBOW:
                    weaponGroup = "Shortbow";
                    break;
                case SHORTSWORD:
                    weaponGroup = "Short Sword";
                    break;
                case SHURIKEN:
                    weaponGroup = "Shuriken";
                    break;
                case SLING:
                    weaponGroup = "Sling";
                    break;
                case SPEAR:
                    weaponGroup = "Spear";
                    break;
                case STAFF:
                    weaponGroup = "Staff";
                    break;
                case UNARMED:
                    weaponGroup = "Unarmed";
                    break;
            }
        }
        return weaponGroup;
    }
}
