package com.sheeter.azuris.sheeter4e.Modules;

public enum VisionType {
    NORMAL,
    LOW_LIGHT,
    DARKVISION,;

    public static String getRaw(VisionType visionType) {
        String vision = "N/A";
        if (visionType != null) {
            switch (visionType) {
                case NORMAL:
                    vision = "Normal Vision";
                    break;
                case LOW_LIGHT:
                    vision = "Low-Light Vision";
                    break;
                case DARKVISION:
                    vision = "Darkvision";
                    break;
            }
        }
        return vision;
    }
}
