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
                    skill = "Acrobatics";
                    break;
                case ARCANA:
                    skill = "Arcana";
                    break;
                case ATHLETICS:
                    skill = "Athletics";
                    break;
                case BLUFF:
                    skill = "Bluff";
                    break;
                case DIPLOMACY:
                    skill = "Diplomacy";
                    break;
                case DUNGEONEERING:
                    skill = "Dungeoneering";
                    break;
                case ENDURANCE:
                    skill = "Endurance";
                    break;
                case HEAL:
                    skill = "Heal";
                    break;
                case HISTORY:
                    skill = "History";
                    break;
                case INSIGHT:
                    skill = "Insight";
                    break;
                case INTIMIDATE:
                    skill = "Intimidate";
                    break;
                case NATURE:
                    skill = "Nature";
                    break;
                case PERCEPTION:
                    skill = "Perception";
                    break;
                case RELIGION:
                    skill = "Religion";
                    break;
                case STEALTH:
                    skill = "Stealth";
                    break;
                case STREETWISE:
                    skill = "Streetwise";
                    break;
                case THIEVERY:
                    skill = "Thievery";
                    break;
            }
        }
        return skill;
    }
}
