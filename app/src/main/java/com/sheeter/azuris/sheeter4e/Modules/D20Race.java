package com.sheeter.azuris.sheeter4e.Modules;

import java.util.ArrayList;
import java.util.Arrays;

public class D20Race {
    private String title;
    private String description;
    private ArrayList<Trait> racialTraits;
    private ArrayList<String> abilityScoreBonuses;
    private ArrayList<String> skillBonuses;
    private SizeType sizeType;
    private int speed;
    private VisionType visionType;
    private ArrayList<String> languages;
    private int minHeight; // In inches
    private int maxHeight; // In inches
    private int minWeight; // In pounds
    private int maxWeight; // In pounds
    private ArrayList<D20Class> favoredClasses;

    public D20Race() { }

    public D20Race(String title, String description, Trait[] racialTraits, String[] abilityScoreBonuses, String[] skillBonuses, SizeType sizeType, int speed, VisionType visionType, String[] languages, int minHeight, int maxHeight, int minWeight, int maxWeight, D20Class[] favoredClasses) {
        this.racialTraits = new ArrayList<>();
        this.abilityScoreBonuses = new ArrayList<>();
        this.skillBonuses = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.favoredClasses = new ArrayList<>();
        this.title = title;
        this.description = description;
        this.racialTraits.addAll(Arrays.asList(racialTraits));
        this.abilityScoreBonuses.addAll(Arrays.asList(abilityScoreBonuses));
        this.skillBonuses.addAll(Arrays.asList(skillBonuses));
        this.sizeType = sizeType;
        this.speed = speed;
        this.visionType = visionType;
        this.languages.addAll(Arrays.asList(languages));
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.favoredClasses.addAll(Arrays.asList(favoredClasses));
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Trait getRacialTrait(int index) {
        return racialTraits.get(index);
    }

    public String getAbilityScoreBonus(int index) {
        return abilityScoreBonuses.get(index);
    }

    public String getSkillBonus(int index) {
        return skillBonuses.get(index);
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public int getSpeed() {
        return speed;
    }

    public VisionType getVisionType() {
        return visionType;
    }

    public String getLanguage(int index) {
        return languages.get(index);
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinWeight() {
        return minWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public D20Class getFavoredClass(int index) {
        return favoredClasses.get(index);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRacialTraits(Trait[] racialTraits) {
        this.racialTraits.addAll(Arrays.asList(racialTraits));
    }

    public void setAbilityScoreBonuses(String[] abilityScoreBonuses) {
        this.abilityScoreBonuses.addAll(Arrays.asList(abilityScoreBonuses));
    }

    public void setSkillBonuses(String[] skillBonuses) {
        this.skillBonuses.addAll(Arrays.asList(skillBonuses));
    }

    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setVisionType(VisionType visionType) {
        this.visionType = visionType;
    }

    public void setLanguages(String[] languages) {
        this.languages.addAll(Arrays.asList(languages));
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMinWeight(int minWeight) {
        this.minWeight = minWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setFavoredClasses(D20Class[] favoredClasses) {
        this.favoredClasses.addAll(Arrays.asList(favoredClasses));
    }
}
