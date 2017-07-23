package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.WeaponBonus;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-07-22.
 */

public class EquipedWeaponsListViewAdapter implements ListAdapter {
    Context ctx;
    ArrayList<TextView> items;

    public EquipedWeaponsListViewAdapter(Context ctx) {
        super();
        this.ctx = ctx;
        this.items = new ArrayList<>();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) { }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) { }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.items.get(i).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.items.get(i);
        }

        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.items.size() == 0;
    }

    public void addItem(WeaponBonus weapon) {
        TextView attack0 = new TextView(ctx);
        TextView attack1 = new TextView(ctx);
        TextView attack2 = new TextView(ctx);
        String name = weapon.getWeaponName();
        String atk = getHitChance(weapon);
        String dam = weapon.getDamage();
        SpannableStringBuilder eq = bold("EQP: ", name);
        SpannableStringBuilder at = bold("ATK: ", atk);
        SpannableStringBuilder dm = bold("DAM: ", dam);
        attack0.setText(eq);
        attack0.setTextColor(Color.BLACK);
        attack1.setText(at);
        attack1.setTextColor(Color.BLACK);
        attack2.setText(dm);
        attack2.setTextColor(Color.BLACK);

        this.items.add(attack0);
        this.items.add(attack1);
        this.items.add(attack2);
    }

    public void removeItem(int i) {
        this.items.remove(i);
    }

    private SpannableStringBuilder bold(String prefix, String content) {
        SpannableStringBuilder sb = new SpannableStringBuilder(prefix + content);
        StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        sb.setSpan(b, 0, prefix.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make some characters Bold
        return sb;
    }

    private String getHitChance(WeaponBonus weaponBonus) {
        String attackStat = weaponBonus.getAttackStat();
        String hitComponents = weaponBonus.getHitComponents();
        int attackTotal = 0;

        if (hitComponents.length() > 0) {
            String[] components = hitComponents.split("\n");
            for (String component : components) {
                int additive = Integer.parseInt(component.split(" ")[0]);
                attackTotal += additive;
            }
        }

        if (attackTotal != 0)
            if (attackTotal > 0)
                attackStat = attackStat.concat(" + " + attackTotal);
            else
                attackStat = attackStat.concat(" - " + attackTotal);

        return attackStat;
    }
}
