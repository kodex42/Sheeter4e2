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

    public String getRole() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < primaryRoles.size(); i++) {
            s.append(Role.getRaw(primaryRoles.get(i)));

            if (i < primaryRoles.size() - 1) {
                s.append(", ");
            }
        }
        return s.toString();
    }

    public String getPowerSource() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < powerSources.size(); i++) {
            s.append(PowerSource.getRaw(powerSources.get(i)));

            if (i < powerSources.size() - 1) {
                s.append(", ");
            }
        }
        return s.toString();
    }

    public String getKeyAbilities() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < keyAbilities.size(); i++) {
            s.append(AbilityType.getRaw(keyAbilities.get(i)));

            if (i < keyAbilities.size() - 1) {
                s.append(", ");
            }
        }
        return s.toString();
    }

    public String getArmorProficiencies() {
        StringBuilder s = new StringBuilder();

        s.append(ArmorType.getRaw(armorProficiency));
        if (shieldProficiency != null)
            s.append(", ").append(ShieldType.getRaw(shieldProficiency));

        return s.toString();
    }

    public String getWeaponProficiencies() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < weaponTypeProficiencies.size(); i++) {
            s.append(weaponTypeProficiencies.get(i).toString());
        }

        return s.toString();
    }

    public boolean hasImplements() {
        return implementTypeProficiencies.size() > 0;
    }

    public String getImplements() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < implementTypeProficiencies.size(); i++) {
            s.append(ImplementType.getRaw(implementTypeProficiencies.get(i)));

            if (i < implementTypeProficiencies.size() - 1) {
                s.append(", ");
            }
        }

        return s.toString();
    }

    public String getDefenseBonuses() {
        int fort = 0;
        int refl = 0;
        int will = 0;

        for (int i = 0; i < defenseBonuses.size(); i++) {
            switch (defenseBonuses.get(i)){
                case WILL:
                    will++;
                    break;
                case FORTITUDE:
                    fort++;
                    break;
                case REFLEX:
                    refl++;
                    break;
            }
        }

        StringBuilder s = new StringBuilder();

        if (fort > 0)
            s.append("+").append(fort).append(" Fortitude").append(", ");

        if (refl > 0)
            s.append("+").append(refl).append(" Reflex").append(", ");

        if (will > 0)
            s.append("+").append(will).append(" Will").append(", ");

        return s.toString().substring(0, s.length()-2);
    }

    public String getBaseHitpoints() {
        return String.valueOf(baseHitPoints) + " + " + "Constitution modifier";
    }

    public String getHitpointGains() {
        return String.valueOf(hitPointsPerLevel);
    }

    public String getDailySurges() {
        return String.valueOf(baseHealingSurges) + " + " + "Constitution modifier";
    }

    public String getTrainedSkills() {
        StringBuilder s = new StringBuilder();

        if (trainedSkills.size() > 0) {
            for (int i = 0; i < trainedSkills.size(); i++) {
                switch (trainedSkills.get(i)) {
                    case XOR:
                        s.append(" or ");
                        break;
                    case AND:
                        s.append(" and ");
                        break;
                    default:
                        s.append(SkillType.getRaw(trainableSkills.get(i)).substring(0, SkillType.getRaw(trainableSkills.get(i)).length() - 6));
                }
            }
            s.append(".\n");
        }

        s.append("From the class skills list below, choose ").append(numberToWord(numTrainableSkills)).append(" more trained skills at 1st level:\n");

        for (int i = 0; i < trainableSkills.size(); i++) {
            s.append(SkillType.getRaw(trainableSkills.get(i)));

            if (i < trainableSkills.size() - 1)
                s.append("\n");
        }

        return s.toString();
    }

    // Currently only used for number of trainable skills, which can only be 3 or 4
    private String numberToWord(int x) {
        switch (x) {
            case 3:
                return "three";
            case 4:
                return "four";
        }
        return String.valueOf(x);
    }
}
