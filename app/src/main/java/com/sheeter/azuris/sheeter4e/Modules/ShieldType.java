package com.sheeter.azuris.sheeter4e.Modules;

// Heavy implies Light as well
public enum ShieldType {
    LIGHT,
    HEAVY,;

    public static String getRaw(ShieldType shieldType) {
        String shield = "N/A";
        if (shieldType != null) {
            switch (shieldType) {
                case LIGHT:
                    shield = "Light Shield";
                    break;
                case HEAVY:
                    shield = "Heavy Shield";
                    break;
            }
        }
        return shield;
    }
}
