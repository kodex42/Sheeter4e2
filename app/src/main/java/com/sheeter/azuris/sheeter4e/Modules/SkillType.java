package com.sheeter.azuris.sheeter4e.Modules;

public enum SkillType {
    ACROBATICS,
    ARCANA,
    ATHLETICS,
    BLUFF,
    DIPLOMACY,
    DUNGEONEERING,
    ENDURANCE,
    HEAL,
    HISTORY,
    INSIGHT,
    INTIMIDATE,
    NATURE,
    PERCEPTION,
    RELIGION,
    STEALTH,
    STREETWISE,
    THIEVERY,
    AND,
    XOR,;

    public static String getRaw(SkillType skillType) {
        String skill = "N/A";
        if (skillType != null) {
            switch (skillType) {
                case ACROBATICS:
                    skill = "Acrobatics (DEX)";
                    break;
                case ARCANA:
                    skill = "Arcana (INT)";
                    break;
                case ATHLETICS:
                    skill = "Athletics (STR)";
                    break;
                case BLUFF:
                    skill = "Bluff (CHA)";
                    break;
                case DIPLOMACY:
                    skill = "Diplomacy (CHA)";
                    break;
                case DUNGEONEERING:
                    skill = "Dungeoneering (WIS)";
                    break;
                case ENDURANCE:
                    skill = "Endurance (CON)";
                    break;
                case HEAL:
                    skill = "Heal (WIS)";
                    break;
                case HISTORY:
                    skill = "History (INT)";
                    break;
                case INSIGHT:
                    skill = "Insight (WIS)";
                    break;
                case INTIMIDATE:
                    skill = "Intimidate (CHA)";
                    break;
                case NATURE:
                    skill = "Nature (WIS)";
                    break;
                case PERCEPTION:
                    skill = "Perception (WIS)";
                    break;
                case RELIGION:
                    skill = "Religion (INT)";
                    break;
                case STEALTH:
                    skill = "Stealth (DEX)";
                    break;
                case STREETWISE:
                    skill = "Streetwise (CHA)";
                    break;
                case THIEVERY:
                    skill = "Thievery (DEX)";
                    break;
            }
        }
        return skill;
    }
}
