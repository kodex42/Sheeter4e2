package com.sheeter.azuris.sheeter4e.Modules;

public enum AbilityType {
    CHARISMA,
    CONSTITUTION,
    DEXTERITY,
    INTELLIGENCE,
    STRENGTH,
    WISDOM,;

    public static String getRaw(AbilityType abilityType) {
        String ability = "N/A";
        if (abilityType != null) {
            switch (abilityType) {
                case CHARISMA:
                    ability = "Charisma";
                    break;
                case CONSTITUTION:
                    ability = "Constitution";
                    break;
                case DEXTERITY:
                    ability = "Dexterity";
                    break;
                case INTELLIGENCE:
                    ability = "Intelligence";
                    break;
                case STRENGTH:
                    ability = "Strength";
                    break;
                case WISDOM:
                    ability = "Wisdom";
                    break;
            }
        }
        return ability;
    }
}
