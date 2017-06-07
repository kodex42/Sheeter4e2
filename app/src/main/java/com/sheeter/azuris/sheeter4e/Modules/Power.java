package com.sheeter.azuris.sheeter4e.Modules;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-05.
 */

public class Power {
    private String name;
    private Frequency frequency;
    private ActionType actionType;
    private ArrayList<WeaponBonus> weaponBonuses;
    private DamageType damageType;

    public Power() {
        weaponBonuses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ArrayList<WeaponBonus> getWeaponBonuses() {
        return weaponBonuses;
    }

    public void setWeaponBonuses(ArrayList<WeaponBonus> weaponBonuses) {
        this.weaponBonuses = weaponBonuses;
    }

    public void addBonus(WeaponBonus bonus){
        if (bonus == null)
            return;

        this.weaponBonuses.add(bonus);
    }

    public Frequency stringToFrequency(String freq){
        switch (freq){
            case "At-Will":
                return Frequency.AT_WILL;
            case "Encounter":
                return Frequency.ENCOUNTER;
            case "Daily":
                return Frequency.DAILY;
            case "Encounter (Special)":
                return Frequency.ENCOUNTER_SPECIAL;
            default:
                return null;
        }
    }

    public ActionType stringToActionType(String actionType){
        switch (actionType){
            case "Standard Action":
                return ActionType.STANDARD;
            case "Minor Action":
                return ActionType.MINOR;
            case "Immediate Interrupt":
                return ActionType.INTERRUPT;
            case "Immediate Reaction":
                return ActionType.REACTION;
            default:
                return null;
        }
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public DamageType stringToDamageType(String type){
        switch (type) {
            case "Psychic":
                return DamageType.PSYCHIC;
            case "Acid":
                return DamageType.ACID;
            case "Physical":
                return DamageType.PHYSICAL;
            case "Force":
                return DamageType.FORCE;
            case "Fire":
                return DamageType.FIRE;
            case "Cold":
                return DamageType.COLD;
            case "Lightning":
                return DamageType.LIGHTNING;
            case "Thunder":
                return DamageType.THUNDER;
            case "Poison":
                return DamageType.POISON;
            case "Necrotic":
                return DamageType.NECROTIC;
            case "Radiant":
                return DamageType.RADIANT;
            default:
                return null;
        }
    }
}
