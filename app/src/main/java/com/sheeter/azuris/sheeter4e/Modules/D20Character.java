package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Azuris on 2017-06-03.
 */

public class D20Character {
    private String legality;
    public Sheet sheet;

    public D20Character(String legality) {
        this.legality = legality;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }
}
