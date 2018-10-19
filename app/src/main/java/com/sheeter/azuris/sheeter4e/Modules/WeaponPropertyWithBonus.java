package com.sheeter.azuris.sheeter4e.Modules;

public class WeaponPropertyWithBonus {
    private Property property;
    private int bonus;

    public WeaponPropertyWithBonus(Property property, int bonus) {
        this.property = property;
        this.bonus = bonus;
    }

    public Property getProperty() {
        return property;
    }

    public int getBonus() {
        return bonus;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
