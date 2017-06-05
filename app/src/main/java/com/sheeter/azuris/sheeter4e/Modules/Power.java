package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Azuris on 2017-06-05.
 */

public class Power {
    private String name;
    private Frequency frequency;

    public Power(String name, Frequency frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public Frequency getFrequency() {
        return frequency;
    }
}
