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
    public ArrayList<Item> items;
    public ArrayList<Power> powers;

    public void setDetails(Details details) {
        this.details = details;
    }

    public void setAbilityScores(AbilityScores abilityScores) {
        this.abilityScores = abilityScores;
    }
}
