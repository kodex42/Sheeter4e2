package com.sheeter.azuris.sheeter4e.Modules;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-03.
 */

public class Sheet {
    public Details details;
    public AbilityScores abilityScores;
    public ArrayList<Stat> stats;

    public void setDetails(Details details) {
        this.details = details;
    }

    public void setAbilityScores(AbilityScores abilityScores) {
        this.abilityScores = abilityScores;
    }
}
