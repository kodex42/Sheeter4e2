package com.sheeter.azuris.sheeter4e.Modules;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Azuris on 2017-06-03.
 */

public class Sheet {
    public Details details;
    public AbilityScores abilityScores;
    public Map<String,String> stats;
    public Map<String,Defense> defenses;
    public ArrayList<Item> items;
    public ArrayList<Power> powers;

    public void setDetails(Details details) {
        this.details = details;
    }

    public void setAbilityScores(AbilityScores abilityScores) {
        this.abilityScores = abilityScores;
    }

    public ArrayList<Power> getPowers() {
        return powers;
    }

    public Item[] getEquipedWeapons() {
        ArrayList<Item> equipedWeapons = new ArrayList<>();

        for (Item item : this.items) {
            if (item.isEquipped() && item.getType() == ItemType.WEAPON) {
                equipedWeapons.add(item);
            }
        }

        Item[] items = new Item[2];
        return equipedWeapons.toArray(items);
    }
}
