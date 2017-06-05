package com.sheeter.azuris.sheeter4e.Modules;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-03.
 */

public class Details {
    private String name;
    private int level;
    private String player;
    private String height;
    private String weight;
    private String gender;
    private int age;
    private String alignment;
    private String company;
    private String portrait;
    private long experience;
    private String carriedMoney;
    private String storedMoney;
    private String race;
    private String size;
    private String vision;
    private String charClass;
    private ArrayList<String> languages = new ArrayList<>();
    private String role;

    public void addLanguage(String lang){
        if (!this.languages.contains(lang))
            this.languages.add(lang);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public void setCarriedMoney(String carriedMoney) {
        this.carriedMoney = carriedMoney;
    }

    public void setStoredMoney(String storedMoney) {
        this.storedMoney = storedMoney;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public String getCharClass() {
        return charClass;
    }

    public String getRace() {
        return race;
    }

    public String getSize() {
        return size;
    }

    public String getVision() {
        return vision;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getAlignment() {
        return alignment;
    }

    public int getHalfLevel() {
        return level/2;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
