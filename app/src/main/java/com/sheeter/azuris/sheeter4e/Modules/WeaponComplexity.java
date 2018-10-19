package com.sheeter.azuris.sheeter4e.Modules;

public enum WeaponComplexity {
    SIMPLE_MELEE,
    MILITARY_MELEE,
    SUPERIOR_MELEE,
    IMPROVISED_MELEE,
    SIMPLE_RANGED,
    MILITARY_RANGED,
    SUPERIOR_RANGED,
    IMPROVISED_RANGED,;

    public static String getRaw(WeaponComplexity weaponComplexity) {
        String complexity = "N/A";
        if (weaponComplexity != null) {
            switch (weaponComplexity) {
                case SIMPLE_MELEE:
                    complexity = "Simple Melee";
                    break;
                case MILITARY_MELEE:
                    complexity = "Military Melee";
                    break;
                case SUPERIOR_MELEE:
                    complexity = "Superior Melee";
                    break;
                case IMPROVISED_MELEE:
                    complexity = "Improvised Melee";
                    break;
                case SIMPLE_RANGED:
                    complexity = "Simple Ranged";
                    break;
                case MILITARY_RANGED:
                    complexity = "Military Ranged";
                    break;
                case SUPERIOR_RANGED:
                    complexity = "Superior Ranged";
                    break;
                case IMPROVISED_RANGED:
                    complexity = "Improvised Ranged";
                    break;
            }
        }
        return complexity;
    }
}
