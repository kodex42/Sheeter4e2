package com.sheeter.azuris.sheeter4e.Modules;

// From Cloth to Plate, higher proficiencies imply lower ones as well
// ex: Chainmail = Chaimail + Hide + Leather + Cloth
public enum ArmorType {
    CLOTH,
    LEATHER,
    HIDE,
    CHAINMAIL,
    SCALE,
    PLATE,;

    public static String getRaw(ArmorType armorType) {
        String armor = "N/A";
        if (armorType != null) {
            switch (armorType) {
                case CLOTH:
                    armor = "Cloth Armor";
                    break;
                case LEATHER:
                    armor = "Leather Armor";
                    break;
                case HIDE:
                    armor = "Hide Armor";
                    break;
                case CHAINMAIL:
                    armor = "Chainmail";
                    break;
                case SCALE:
                    armor = "Scale Armor";
                    break;
                case PLATE:
                    armor = "Plate Armor";
                    break;
            }
        }
        return armor;
    }
}
