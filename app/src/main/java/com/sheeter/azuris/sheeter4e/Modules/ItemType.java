package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Eric on 6/5/2017.
 */

public enum ItemType {
    WEAPON,
    ARMOR,
    GEAR,
    LOOT,;

    public static String getRaw(ItemType itemType) {
        String type = "N/A";
        if (itemType != null) {
            switch (itemType) {
                case WEAPON:
                    type = "WeaponType";
                    break;
                case ARMOR:
                    type = "Armor";
                    break;
                case GEAR:
                    type = "Gear";
                    break;
                case LOOT:
                    type = "Loot";
                    break;
            }
        }
        return type;
    }
}
