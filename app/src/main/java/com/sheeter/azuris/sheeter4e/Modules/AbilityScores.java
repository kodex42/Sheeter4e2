package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Azuris on 2017-06-03.
 */

public class AbilityScores {
    private int strength;
    private int constitution;
    private int dexterity;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private int strengthMod;
    private int constitutionMod;
    private int dexterityMod;
    private int intelligenceMod;
    private int wisdomMod;
    private int charismaMod;
    private int strengthModHalfLevel;
    private int constitutionModHalfLevel;
    private int dexterityModHalfLevel;
    private int intelligenceModHalfLevel;
    private int wisdomModHalfLevel;
    private int charismaModHalfLevel;

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void setStrengthMod(int strengthMod) {
        this.strengthMod = strengthMod;
    }

    public void setConstitutionMod(int constitutionMod) {
        this.constitutionMod = constitutionMod;
    }

    public void setDexterityMod(int dexterityMod) {
        this.dexterityMod = dexterityMod;
    }

    public void setIntelligenceMod(int intelligenceMod) {
        this.intelligenceMod = intelligenceMod;
    }

    public void setWisdomMod(int wisdomMod) {
        this.wisdomMod = wisdomMod;
    }

    public void setCharismaMod(int charismaMod) {
        this.charismaMod = charismaMod;
    }

    public void setStrengthModHalfLevel(int strengthModHalfLevel) {
        this.strengthModHalfLevel = strengthModHalfLevel;
    }

    public void setConstitutionModHalfLevel(int constitutionModHalfLevel) {
        this.constitutionModHalfLevel = constitutionModHalfLevel;
    }

    public void setDexterityModHalfLevel(int dexterityModHalfLevel) {
        this.dexterityModHalfLevel = dexterityModHalfLevel;
    }

    public void setIntelligenceModHalfLevel(int intelligenceModHalfLevel) {
        this.intelligenceModHalfLevel = intelligenceModHalfLevel;
    }

    public void setWisdomModHalfLevel(int wisdomModHalfLevel) {
        this.wisdomModHalfLevel = wisdomModHalfLevel;
    }

    public void setCharismaModHalfLevel(int charismaModHalfLevel) {
        this.charismaModHalfLevel = charismaModHalfLevel;
    }

    public String getStrength() {
        return String.valueOf(strength);
    }

    public String getConstitution() {
        return String.valueOf(constitution);
    }

    public String getDexterity() {
        return String.valueOf(dexterity);
    }

    public String getIntelligence() {
        return String.valueOf(intelligence);
    }

    public String getWisdom() {
        return String.valueOf(wisdom);
    }

    public String getCharisma() {
        return String.valueOf(charisma);
    }

    public String getStrengthMod() {
        return toStringMod(strengthMod);
    }

    public String getConstitutionMod() {
        return toStringMod(constitutionMod);
    }

    public String getDexterityMod() {
        return toStringMod(dexterityMod);
    }

    public String getIntelligenceMod() {
        return toStringMod(intelligenceMod);
    }

    public String getWisdomMod() {
        return toStringMod(wisdomMod);
    }

    public String getCharismaMod() {
        return toStringMod(charismaMod);
    }

    public String getStrengthModHalfLevel() {
        return toStringMod(strengthModHalfLevel);
    }

    public String getConstitutionModHalfLevel() {
        return toStringMod(constitutionModHalfLevel);
    }

    public String getDexterityModHalfLevel() {
        return toStringMod(dexterityModHalfLevel);
    }

    public String getIntelligenceModHalfLevel() {
        return toStringMod(intelligenceModHalfLevel);
    }

    public String getWisdomModHalfLevel() {
        return toStringMod(wisdomModHalfLevel);
    }

    public String getCharismaModHalfLevel() {
        return toStringMod(charismaModHalfLevel);
    }

    private String toStringMod(int mod) {
        return mod < 0 ? String.valueOf(mod) : "+" + String.valueOf(mod);
    }
}
