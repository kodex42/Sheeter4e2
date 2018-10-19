package com.sheeter.azuris.sheeter4e.Modules;

import java.util.ArrayList;
import java.util.Arrays;

public class WeaponType {
    private ArrayList<WeaponGroupType> group;
    private WeaponComplexity complexity;
    private ArrayList<WeaponPropertyWithBonus> properties;

    public WeaponType(){ }

    public WeaponType(WeaponComplexity complexity) {
        this.complexity = complexity;
    }

    public WeaponType(WeaponGroupType[] group) {
        this.group = new ArrayList<>();
        this.group.addAll(Arrays.asList(group));
    }

    public WeaponType(WeaponGroupType[] group, WeaponComplexity complexity, WeaponPropertyWithBonus[] properties) {
        this.group = new ArrayList<>();
        this.properties = new ArrayList<>();
        this.group.addAll(Arrays.asList(group));
        this.complexity = complexity;
        this.properties.addAll(Arrays.asList(properties));
    }
}
