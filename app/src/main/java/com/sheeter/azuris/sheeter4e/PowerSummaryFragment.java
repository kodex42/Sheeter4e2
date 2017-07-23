package com.sheeter.azuris.sheeter4e;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.ActionType;
import com.sheeter.azuris.sheeter4e.Modules.DamageType;
import com.sheeter.azuris.sheeter4e.Modules.Frequency;
import com.sheeter.azuris.sheeter4e.Modules.Item;
import com.sheeter.azuris.sheeter4e.Modules.Power;
import com.sheeter.azuris.sheeter4e.Modules.WeaponBonus;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-21.
 */

@SuppressLint("ValidFragment")
public class PowerSummaryFragment extends Fragment {
    private Power mPower;

    PowerSummaryFragment(Power mPower) {
        super();
        this.mPower = mPower;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.content_power_summary, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        String mPowerName = mPower.getName();
        ArrayList<Power> mPowers = MainActivity.sCharacter.sheet.getPowers();

        for (int i = 0; i < mPowers.size(); i++) {
            if (mPowers.get(i).getName().equals(mPowerName)) {
                mPower = mPowers.get(i);
                break;
            }
        }

        Frequency freq = mPower.getFrequency();

        int colorId = R.color.black;
        switch (freq) {
            case AT_WILL:
                colorId = R.color.atWillPower;
                break;
            case ENCOUNTER:
            case ENCOUNTER_SPECIAL:
                colorId = R.color.encounterPower;
                break;
            case DAILY:
                colorId = R.color.dailyPower;
        }

        // Color
        root.findViewById(R.id.PowerModalActivity_LinearLayout_Background).setBackgroundColor(getResources().getColor(colorId));
        root.findViewById(R.id.PowerModalActivity_LinearLayout_PowerNameBackground).setBackgroundColor(getResources().getColor(colorId));
        ((TextView)root.findViewById(R.id.PowerModalActivity_TextView_Frequency)).setTextColor(getResources().getColor(colorId));

        // Data
        ((TextView)root.findViewById(R.id.PowerModalActivity_TextView_PowerName)).setText(mPower.getName());
        ((ImageView)root.findViewById(R.id.PowerModalActivity_ImageView_DamageType)).setImageResource(DamageType.getImageId(mPower.getDamageType(), mPower.getWeaponBonuses()));
        ((TextView)root.findViewById(R.id.PowerModalActivity_TextView_ActionType)).setText(ActionType.getRaw(mPower.getActionType()));
        ((TextView)root.findViewById(R.id.PowerModalActivity_TextView_Frequency)).setText(Frequency.getRaw(mPower.getFrequency()));

        ArrayList<WeaponBonus> weapons = mPower.getWeaponBonuses();
        if (weapons.size() > 0) {
            TextView attack = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Attack);
            NonScrollListView equipedWeapons = (NonScrollListView) root.findViewById(R.id.PowerModalActivity_NonScrollListView_EquipedWeapons);
            String atk = "";
            String def = "";
            EquipedWeaponsListViewAdapter adapter = new EquipedWeaponsListViewAdapter(getContext());
            for (WeaponBonus weapon : weapons) {
                if (isEquiped(weapon)) {
                    adapter.addItem(weapon);
                    atk = atk.contains(weapon.getAttackStat()) ? atk : atk.concat(" or " + weapon.getAttackStat());
                    def = weapon.getDefense();
                }
            }
            if (atk.startsWith(" or "))
                atk = atk.substring(4);

            attack.setText(bold("Attack: ", atk + " vs. " + def));
            attack.setVisibility(View.VISIBLE);

            equipedWeapons.setAdapter(adapter);
            equipedWeapons.setVisibility(View.VISIBLE);
        }

        if (mPower.wasQueried()) {
            TextView requirements = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Requirements);
            TextView trigger = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Trigger);
            TextView sustainAction = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_SustainAction);
            TextView weaponBonus = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Weapon_Bonus);
            TextView primaryTarget = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Target);
            TextView secondaryTarget = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_SecondaryTarget);
            TextView secondaryAttack = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_SecondaryAttack);
            TextView secondaryHit = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_SecondaryHit);
            TextView tertiaryTarget = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_TertiaryTarget);
            TextView tertiaryAttack = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_TertiaryAttack);
            TextView tertiaryHit = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_TertiaryHit);
            TextView range = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Range);
            TextView hit = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Hit);
            TextView missEffects = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_MissEffect);
            TextView effects = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Effect);
            TextView specialEffects = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_SpecialEffect);
            TextView damageIncreaseAt11 = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_DamageIncreaseAt11);
            TextView damageIncreaseAt21 = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_DamageIncreaseAt21);
            TextView description = (TextView) root.findViewById(R.id.PowerModalActivity_TextView_Desc);

            if (!mPower.getRequirement().equals("null")) {
                requirements.setText(bold("Requirement: ", mPower.getRequirement()));
                requirements.setVisibility(View.VISIBLE);
            }
            if (!mPower.getTrigger().equals("null")) {
                trigger.setText(bold("Trigger: ", mPower.getTrigger()));
                trigger.setVisibility(View.VISIBLE);
            }
            if (!mPower.getSustainAction().equals("null")) {
                sustainAction.setText(bold("Sustain: ", mPower.getSustainAction()));
                sustainAction.setVisibility(View.VISIBLE);
            }
            if (!mPower.getWeaponBonus().equals("null")) {
                weaponBonus.setText(bold("Weapon: ", mPower.getWeaponBonus()));
                weaponBonus.setVisibility(View.VISIBLE);
            }
            if (!mPower.getPrimaryTarget().equals("null")) {
                primaryTarget.setText(bold("Target: ", mPower.getPrimaryTarget()));
                primaryTarget.setVisibility(View.VISIBLE);
            }
            if (!mPower.getSecondaryTarget().equals("null")) {
                secondaryTarget.setText(bold("Secondary Target: ", mPower.getSecondaryTarget()));
                secondaryTarget.setVisibility(View.VISIBLE);
            }
            if (!mPower.getSecondaryAttack().equals("null")) {
                secondaryAttack.setText(bold("Secondary Attack: ", mPower.getSecondaryAttack()));
                secondaryAttack.setVisibility(View.VISIBLE);
            }
            if (!mPower.getSecondaryHit().equals("null")) {
                secondaryHit.setText(bold("Secondary Hit: ", mPower.getSecondaryHit()));
                secondaryHit.setVisibility(View.VISIBLE);
            }
            if (!mPower.getTertiaryTarget().equals("null")) {
                tertiaryTarget.setText(bold("Tertiary Target: ", mPower.getTertiaryTarget()));
                tertiaryTarget.setVisibility(View.VISIBLE);
            }
            if (!mPower.getTertiaryAttack().equals("null")) {
                tertiaryAttack.setText(bold("Tertiary Attack: ", mPower.getTertiaryAttack()));
                tertiaryAttack.setVisibility(View.VISIBLE);
            }
            if (!mPower.getTertiaryHit().equals("null")) {
                tertiaryHit.setText(bold("Tertiary Hit: ", mPower.getTertiaryHit()));
                tertiaryHit.setVisibility(View.VISIBLE);
            }
            if (!mPower.getRange().equals("null")) {
                range.setText(mPower.getRange());
                range.setVisibility(View.VISIBLE);
            }
            if (!mPower.getHitEffects().equals("null")) {
                WeaponBonus bestDamage = getBestDamage(mPower.getWeaponBonuses());
                if (bestDamage != null && !bestDamage.getDamage().equals(""))
                    hit.setText(bold("Hit: ", bestDamage.getDamage() + ", and " + mPower.getHitEffects().substring(0, 1).toUpperCase() + mPower.getHitEffects().substring(1)));
                else
                    hit.setText(bold("Hit: ", mPower.getHitEffects()));
                hit.setVisibility(View.VISIBLE);
            }
            if (!mPower.getMissEffects().equals("null")) {
                missEffects.setText(bold("Miss: ", mPower.getMissEffects()));
                missEffects.setVisibility(View.VISIBLE);
            }
            if (!mPower.getEffects().equals("null")) {
                effects.setText(bold("Effect: ", mPower.getEffects()));
                effects.setVisibility(View.VISIBLE);
            }
            if (!mPower.getSpecialEffects().equals("null")) {
                specialEffects.setText(bold("Special: ", mPower.getSpecialEffects()));
                specialEffects.setVisibility(View.VISIBLE);
            }
            if (!mPower.getDamageIncreaseAt11().equals("null")) {
                damageIncreaseAt11.setText(bold("Lvl 11: Inc Damage To: ", mPower.getDamageIncreaseAt11()));
                damageIncreaseAt11.setVisibility(View.VISIBLE);
            }
            if (!mPower.getDamageIncreaseAt21().equals("null")) {
                damageIncreaseAt21.setText(bold("Lvl 21: Inc Damage To: ", mPower.getDamageIncreaseAt21()));
                damageIncreaseAt21.setVisibility(View.VISIBLE);
            }
            if (!mPower.getDescription().equals("null")) {
                description.setText(mPower.getDescription());
                description.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean isEquiped(WeaponBonus weapon) {
        ArrayList<Item> equipped = MainActivity.sCharacter.sheet.getEquipedWeapons();
        for (Item item : equipped)
            if (item.getName().equals(weapon.getWeaponName()))
                return true;
        return false;
    }

    public static WeaponBonus getBestDamage(ArrayList<WeaponBonus> weaponBonuses) {
        WeaponBonus bestDamage = new WeaponBonus();
        bestDamage.setDamage("");
        for (WeaponBonus bonus : weaponBonuses) {
            if (damageTotal(bestDamage.getDamage()) < damageTotal(bonus.getDamage())) {
                bestDamage = bonus;
            }
        }
        return bestDamage;
    }

    private static int damageTotal(String damage) {
        int total = 0;
        if (!damage.equals("")) {
            if (damage.contains("+")) {
                String[] temp = damage.split("\\+");
                String[] dice = temp[0].split("d");
                total = Integer.parseInt(dice[0]) * Integer.parseInt(dice[1]);

                if (temp.length > 1)
                    for (int i = 1; i < temp.length; i++)
                        total += Integer.parseInt(temp[i]);
            }
            else if (damage.contains("-")) {
                String[] temp = damage.split("\\-");
                String[] dice = temp[0].split("d");
                total = Integer.parseInt(dice[0]) * Integer.parseInt(dice[1]);

                if (temp.length > 1) {
                    String additive = temp[1];
                    total -= Integer.parseInt(additive);
                }
            }
            else {
                String[] dice = damage.split("d");
                total = Integer.parseInt(dice[0]) * Integer.parseInt(dice[1]);
            }
        }

        return total;
    }

    private SpannableStringBuilder bold(String prefix, String content) {
        SpannableStringBuilder sb = new SpannableStringBuilder(prefix + content);
        StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        sb.setSpan(b, 0, prefix.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make some characters Bold
        return sb;
    }
}
