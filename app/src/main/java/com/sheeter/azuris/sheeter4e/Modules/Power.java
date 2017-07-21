package com.sheeter.azuris.sheeter4e.Modules;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Azuris on 2017-06-05.
 */

public class Power {
    private String name;
    @Nullable private String requirement;
    @Nullable private String trigger;
    @Nullable private String sustainAction;
    @Nullable private String weaponBonus;
    @Nullable private String primaryTarget;
    @Nullable private String secondaryTarget;
    @Nullable private String secondaryAttack;
    @Nullable private String secondaryHit;
    @Nullable private String tertiaryTarget;
    @Nullable private String tertiaryAttack;
    @Nullable private String tertiaryHit;
    @Nullable private String range;
    @Nullable private String hitEffects;
    @Nullable private String missEffects;
    @Nullable private String effects;
    @Nullable private String specialEffects;
    @Nullable private String damageIncreaseAt11;
    @Nullable private String damageIncreaseAt21;
    @Nullable private String description;
    @Nullable private String paragon;
    private boolean wasQueried = false;
    private Frequency frequency;
    private ActionType actionType;
    private ArrayList<WeaponBonus> weaponBonuses;
    private ArrayList<String> prerequisites;
    private DamageType damageType;
    private boolean casted;
    private int requiredLevel;

    public Power() {
        this.weaponBonuses = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        this.casted = false;
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
            case "Free Action":
                return ActionType.FREE;
            case "No Action":
                return ActionType.PASSIVE;
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

    public void setRequirement(@Nullable String requirement) {
        this.requirement = requirement;
    }

    public void setTrigger(@Nullable String trigger) {
        this.trigger = trigger;
    }

    public void setSustainAction(@Nullable String sustainAction) {
        this.sustainAction = sustainAction;
    }

    public void setWeaponBonus(@Nullable String weaponBonus) {
        this.weaponBonus = weaponBonus;
    }

    public void setPrimaryTarget(@Nullable String primaryTarget) {
        this.primaryTarget = primaryTarget;
    }

    public void setSecondaryTarget(@Nullable String secondaryTarget) {
        this.secondaryTarget = secondaryTarget;
    }

    public void setSecondaryAttack(@Nullable String secondaryAttack) {
        this.secondaryAttack = secondaryAttack;
    }

    public void setSecondaryHit(@Nullable String secondaryHit) {
        this.secondaryHit = secondaryHit;
    }

    public void setTertiaryTarget(@Nullable String tertiaryTarget) {
        this.tertiaryTarget = tertiaryTarget;
    }

    public void setTertiaryAttack(@Nullable String tertiaryAttack) {
        this.tertiaryAttack = tertiaryAttack;
    }

    public void setTertiaryHit(@Nullable String tertiaryHit) {
        this.tertiaryHit = tertiaryHit;
    }

    public void setRange(@Nullable String range) {
        this.range = range;
    }

    public void setHitEffects(@Nullable String hitEffects) {
        this.hitEffects = hitEffects;
    }

    public void setMissEffects(@Nullable String missEffects) {
        this.missEffects = missEffects;
    }

    public void setEffects(@Nullable String effects) {
        this.effects = effects;
    }

    public void setSpecialEffects(@Nullable String specialEffects) {
        this.specialEffects = specialEffects;
    }

    public void setDamageIncreaseAt11(@Nullable String damageIncreaseAt11) {
        this.damageIncreaseAt11 = damageIncreaseAt11;
    }

    public void setDamageIncreaseAt21(@Nullable String damageIncreaseAt21) {
        this.damageIncreaseAt21 = damageIncreaseAt21;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public void setParagon(@Nullable String paragon) {
        this.paragon = paragon;
    }

    public void setWasQueried(boolean wasQueried) {
        this.wasQueried = wasQueried;
    }

    @Nullable
    public String getRequirement() {
        return requirement;
    }

    @Nullable
    public String getTrigger() {
        return trigger;
    }

    @Nullable
    public String getSustainAction() {
        return sustainAction;
    }

    @Nullable
    public String getWeaponBonus() {
        return weaponBonus;
    }

    @Nullable
    public String getPrimaryTarget() {
        return primaryTarget;
    }

    @Nullable
    public String getSecondaryTarget() {
        return secondaryTarget;
    }

    @Nullable
    public String getSecondaryAttack() {
        return secondaryAttack;
    }

    @Nullable
    public String getSecondaryHit() {
        return secondaryHit;
    }

    @Nullable
    public String getTertiaryTarget() {
        return tertiaryTarget;
    }

    @Nullable
    public String getTertiaryAttack() {
        return tertiaryAttack;
    }

    @Nullable
    public String getTertiaryHit() {
        return tertiaryHit;
    }

    @Nullable
    public String getRange() {
        return range;
    }

    @Nullable
    public String getHitEffects() {
        return hitEffects;
    }

    @Nullable
    public String getMissEffects() {
        return missEffects;
    }

    @Nullable
    public String getEffects() {
        return effects;
    }

    @Nullable
    public String getSpecialEffects() {
        return specialEffects;
    }

    @Nullable
    public String getDamageIncreaseAt11() {
        return damageIncreaseAt11;
    }

    @Nullable
    public String getDamageIncreaseAt21() {
        return damageIncreaseAt21;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getParagon() {
        return paragon;
    }

    public boolean wasQueried() {
        return wasQueried;
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

    public void cast() {
        this.casted = true;
    }

    public void refresh() {
        this.casted = false;
    }

    public boolean isCasted() {
        return casted;
    }

    public void setPrerequisite(String prerequisite) {
        String[] prereqs = prerequisite.split("&");
        this.requiredLevel = Integer.parseInt(prereqs[0].replaceFirst("Lvl ", ""));
        if (prereqs.length > 1)
            this.prerequisites.addAll(Arrays.asList(prereqs).subList(1, prereqs.length));
    }
}
