package com.sheeter.azuris.sheeter4e.Modules;

public enum PowerSource {
    ARCANE,
    DIVINE,
    MARTIAL,
    PRIMAL,
    PSIONIC,
    SHADOW,;

    public static String getRaw(PowerSource powerSource) {
        String source = "N/A";
        if (powerSource != null) {
            switch (powerSource) {
                case ARCANE:
                    source = "Arcane";
                    break;
                case DIVINE:
                    source = "Divine";
                    break;
                case MARTIAL:
                    source = "Martial";
                    break;
                case PRIMAL:
                    source = "Primal";
                    break;
                case PSIONIC:
                    source = "Psionic";
                    break;
                case SHADOW:
                    source = "Shadow";
                    break;
            }
        }
        return source;
    }
}
