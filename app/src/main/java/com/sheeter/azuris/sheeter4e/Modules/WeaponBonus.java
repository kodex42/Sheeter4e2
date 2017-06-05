package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Eric on 6/5/2017.
 */

public class WeaponBonus {
    private String weaponName;
    private int attackBonus;
    private String damage;
    private String attackStat;
    private String defense;
    private String hitComponents;
    private String damageComponents;

    public WeaponBonus() {
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getAttackStat() {
        return attackStat;
    }

    public void setAttackStat(String attackStat) {
        this.attackStat = attackStat;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getHitComponents() {
        return hitComponents;
    }

    public void setHitComponents(String hitComponents) {
        this.hitComponents = hitComponents;
    }

    public String getDamageComponents() {
        return damageComponents;
    }

    public void setDamageComponents(String damageComponents) {
        this.damageComponents = damageComponents;
    }
}
