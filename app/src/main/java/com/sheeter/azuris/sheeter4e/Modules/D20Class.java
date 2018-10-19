package com.sheeter.azuris.sheeter4e.Modules;

import java.util.ArrayList;
import java.util.Arrays;

public class D20Class {
    public String title;
    public ArrayList<PowerSource> powerSources;
    public ArrayList<Role> primaryRoles;
    public ArrayList<AbilityType> keyAbilities;
    public ArrayList<DefenseType> defenseBonuses; // Each class receives a +1 bonus to certain defenses
    public int baseHitPoints;
    public int hitPointsPerLevel;
    public int baseHealingSurges;
    public ArmorType armorProficiency;
    public ShieldType shieldProficiency;
    public ArrayList<WeaponType> weaponTypeProficiencies;
    public ArrayList<ImplementType> implementTypeProficiencies;
    public ArrayList<SkillType> trainedSkills; // Some classes start with already trained skills
    public int numTrainableSkills;
    public ArrayList<SkillType> trainableSkills;
    public ArrayList<Feat> startingFeats;
    public boolean isSubclass;

    public D20Class() { }

    public D20Class(String title, PowerSource[] powerSources, Role[] primaryRoles, AbilityType[] keyAbilities, DefenseType[] defenseBonuses, int baseHitPoints, int hitPointsPerLevel, int baseHealingSurges, ArmorType armorProficiency, ShieldType shieldProficiency, WeaponType[] weaponTypeProficiencies, ImplementType[] implementTypeProficiencies, SkillType[] trainedSkills, int numTrainableSkills, SkillType[] trainableSkills, Feat[] startingFeats, boolean isSubclass) {
        this.powerSources = new ArrayList<>();
        this.primaryRoles = new ArrayList<>();
        this.keyAbilities = new ArrayList<>();
        this.defenseBonuses = new ArrayList<>();
        this.weaponTypeProficiencies = new ArrayList<>();
        this.implementTypeProficiencies = new ArrayList<>();
        this.trainedSkills = new ArrayList<>();
        this.trainableSkills = new ArrayList<>();
        this.startingFeats = new ArrayList<>();
        this.title = title;
        this.powerSources.addAll(Arrays.asList(powerSources));
        this.primaryRoles.addAll(Arrays.asList(primaryRoles));
        this.keyAbilities.addAll(Arrays.asList(keyAbilities));
        this.defenseBonuses.addAll(Arrays.asList(defenseBonuses));
        this.baseHitPoints = baseHitPoints;
        this.hitPointsPerLevel = hitPointsPerLevel;
        this.baseHealingSurges = baseHealingSurges;
        this.armorProficiency = armorProficiency;
        this.shieldProficiency = shieldProficiency;
        this.weaponTypeProficiencies.addAll(Arrays.asList(weaponTypeProficiencies));
        this.implementTypeProficiencies.addAll(Arrays.asList(implementTypeProficiencies));
        this.trainedSkills.addAll(Arrays.asList(trainedSkills));
        this.numTrainableSkills = numTrainableSkills;
        this.trainableSkills.addAll(Arrays.asList(trainableSkills));
        this.startingFeats.addAll(Arrays.asList(startingFeats));
        this.isSubclass = isSubclass;
    }
}
