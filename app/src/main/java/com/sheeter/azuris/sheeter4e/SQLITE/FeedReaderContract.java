package com.sheeter.azuris.sheeter4e.SQLITE;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Powers";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_REQUIREMENT = "Requirement";
        public static final String COLUMN_NAME_TRIGGER = "Trigger";
        public static final String COLUMN_NAME_SUSTAIN_ACTION = "SustainAction";
        public static final String COLUMN_NAME_WEAPON_BONUS = "WeaponBonus";
        public static final String COLUMN_NAME_PRIMARY_TARGET = "PrimaryTarget";
        public static final String COLUMN_NAME_SECONDARY_TARGET = "SecondaryTarget";
        public static final String COLUMN_NAME_SECONDARY_ATTACK = "SecondaryAttack";
        public static final String COLUMN_NAME_SECONDARY_HIT = "SecondaryHit";
        public static final String COLUMN_NAME_TERTIARY_TARGET = "TertiaryTarget";
        public static final String COLUMN_NAME_TERTIARY_ATTACK = "TertiaryAttack";
        public static final String COLUMN_NAME_TERTIARY_HIT = "TertiaryHit";
        public static final String COLUMN_NAME_RANGE = "Range";
        public static final String COLUMN_NAME_HIT_EFFECTS = "HitEffects";
        public static final String COLUMN_NAME_MISS_EFFECTS = "MissEffects";
        public static final String COLUMN_NAME_EFFECTS = "Effects";
        public static final String COLUMN_NAME_SPECIAL_EFFECTS = "SpecialEffects";
        public static final String COLUMN_NAME_DAMAGE_INCREASE_AT_11 = "DamageIncreaseAt11";
        public static final String COLUMN_NAME_DAMAGE_INCREASE_AT_21 = "DamageIncreaseAt21";
        public static final String COLUMN_NAME_PARAGON = "Paragon";
        public static final String COLUMN_NAME_DESCRIPTION = "Description";
    }
}