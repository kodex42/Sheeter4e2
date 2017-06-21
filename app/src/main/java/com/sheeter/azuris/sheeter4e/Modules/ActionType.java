package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Eric on 6/5/2017.
 */

public enum ActionType {
    STANDARD,
    INTERRUPT,
    MINOR,
    REACTION;

    public static String getRaw(ActionType actionType) {
        String action = "N/A";
        if (actionType != null) {
            switch (actionType) {
                case STANDARD:
                    action = "Standard";
                    break;
                case INTERRUPT:
                    action = "Interrupt";
                    break;
                case MINOR:
                    action = "Minor";
                    break;
                case REACTION:
                    action = "Reaction";
            }
        }
        return action;
    }
}
