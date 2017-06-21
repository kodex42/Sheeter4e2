package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Azuris on 2017-06-05.
 */

public enum Frequency {
    AT_WILL,
    ENCOUNTER,
    ENCOUNTER_SPECIAL,
    DAILY;

    public static String getRaw(Frequency frequency) {
        String freq = "N/A";
        switch (frequency) {
            case AT_WILL:
                freq = "At-Will";
                break;
            case ENCOUNTER:
                freq = "Encounter";
                break;
            case ENCOUNTER_SPECIAL:
                freq = "Encounter (Special)";
                break;
            case DAILY:
                freq = "Daily";
        }
        return freq;
    }
}
