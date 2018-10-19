package com.sheeter.azuris.sheeter4e.Modules;

public enum Role {
    CONTROLLER,
    LEADER,
    DEFENDER,
    STRIKER,;

    public static String getRaw(Role role) {
        String roleName = "N/A";
        if (role != null) {
            switch (role) {
                case CONTROLLER:
                    roleName = "Controller";
                    break;
                case LEADER:
                    roleName = "Leader";
                    break;
                case DEFENDER:
                    roleName = "Defender";
                    break;
                case STRIKER:
                    roleName = "Striker";
                    break;
            }
        }
        return roleName;
    }
}
