package com.sheeter.azuris.sheeter4e.Modules;

import com.sheeter.azuris.sheeter4e.PowerSummaryFragment;
import com.sheeter.azuris.sheeter4e.R;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-06.
 */

public enum DamageType {
    SLASHING,
    BLUDGEONING,
    PIERCING,
    PHYSICAL,
    FORCE,
    FIRE,
    COLD,
    LIGHTNING,
    THUNDER,
    POISON,
    ACID,
    PSYCHIC,
    NECROTIC,
    RADIANT,;

    public static int getImageId(DamageType damageType, ArrayList<WeaponBonus> weaponBonuses) {
        int resId = R.drawable.type_slashing_framed;
        switch (damageType) {
            case SLASHING:
                resId = R.drawable.type_slashing_framed;
                break;
            case BLUDGEONING:
                resId = R.drawable.type_bludgeoning_framed;
                break;
            case PIERCING:
                resId = R.drawable.type_piercing_framed;
                break;
            case PHYSICAL:
                if (weaponBonuses.size() == 0) {
                    resId = R.drawable.type_utility_framed;
                    break;
                } else {
                    WeaponBonus bestDamage = PowerSummaryFragment.getBestDamage(weaponBonuses);
                    if (bestDamage.getDamage().equals(""))
                        resId = R.drawable.type_utility_framed;
                    else
                        resId = R.drawable.type_physical_framed;
                }
                break;
            case FORCE:
                resId = R.drawable.type_force_framed;
                break;
            case FIRE:
                resId = R.drawable.type_fire_framed;
                break;
            case COLD:
                resId = R.drawable.type_cold_framed;
                break;
            case LIGHTNING:
                resId = R.drawable.type_lightning_framed;
                break;
            case THUNDER:
                resId = R.drawable.type_thunder_framed;
                break;
            case POISON:
                resId = R.drawable.type_poison_framed;
                break;
            case ACID:
                resId = R.drawable.type_acid_framed;
                break;
            case PSYCHIC:
                resId = R.drawable.type_psychic_framed;
                break;
            case NECROTIC:
                resId = R.drawable.type_necrotic_framed;
                break;
            case RADIANT:
                resId = R.drawable.type_radiant_framed;
                break;
        }
        return resId;
    }
}

/*
    Acid. The corrosive spray of a black dragon’s breath and the dissolving enzymes secreted by a black pudding deal acid damage.

    Bludgeoning. Blunt force attacks—hammers, falling, constriction, and the like—deal bludgeoning damage.

    Cold. The infernal chill radiating from an ice devil’s spear and the frigid blast of a white dragon’s breath deal cold damage.

    Fire. Red dragons breathe fire, and many spells conjure flames to deal fire damage.

    Force. Force is pure magical energy focused into a damaging form. Most effects that deal force damage are spells, including magic missile and spiritual weapon.

    Lightning. A lightning bolt spell and a blue dragon’s breath deal lightning damage.

    Necrotic. Necrotic damage, dealt by certain undead and a spell such as chill touch, withers matter and even the soul.

    Piercing. Puncturing and impaling attacks, including spears and monsters’ bites, deal piercing damage.

    Poison. Venomous stings and the toxic gas of a green dragon’s breath deal poison damage.

    Psychic. Mental abilities such as a mind flayer’s psionic blast deal psychic damage.

    Radiant. Radiant damage, dealt by a cleric’s flame strike spell or an angel’s smiting weapon, sears the flesh like fire and overloads the spirit with power.

    Slashing. Swords, axes, and monsters’ claws deal slashing damage.

    Thunder. A concussive burst of sound, such as the effect of the Thunderwave spell, deals thunder damage.
*/
