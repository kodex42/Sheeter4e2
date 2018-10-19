package com.sheeter.azuris.sheeter4e.Modules;

public enum SizeType {
    TINY,
    SMALL,
    MEDIUM,
    LARGE,
    HUGE,
    GARGANTUAN,;

    public static String getRaw(SizeType sizeType) {
        String size = "N/A";
        if (sizeType != null) {
            switch (sizeType) {
                case TINY:
                    size = "Tiny";
                    break;
                case SMALL:
                    size = "Small";
                    break;
                case MEDIUM:
                    size = "Medium";
                    break;
                case LARGE:
                    size = "Large";
                    break;
                case HUGE:
                    size = "Huge";
                    break;
                case GARGANTUAN:
                    size = "Gargantuan";
                    break;
            }
        }
        return size;
    }
}
