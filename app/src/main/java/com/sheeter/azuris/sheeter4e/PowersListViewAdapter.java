package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.DamageType;
import com.sheeter.azuris.sheeter4e.Modules.Frequency;
import com.sheeter.azuris.sheeter4e.Modules.Power;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-05.
 */

class PowersListViewAdapter extends ArrayAdapter<String> {
    private ArrayList<Power> powerList;
    private Context context;

    PowersListViewAdapter(Context context) {
        super(context, R.layout.power_row);
        this.context = context;
        powerList = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Power power = this.powerList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.power_row, null);

        ((TextView) convertView.findViewById(R.id.Power_Name)).setText(power.getName());

        String freq = null;
        Frequency frequency = power.getFrequency();
        if (frequency != null) {
            switch (frequency) {
                case AT_WILL:
                    freq = "At-Will";
                    convertView.setBackgroundResource(R.drawable.border_at_will);
                    break;
                case ENCOUNTER:
                    freq = "Encounter";
                    convertView.setBackgroundResource(R.drawable.border_encounter);
                    break;
                case ENCOUNTER_SPECIAL:
                    freq = "Encounter (Special)";
                    convertView.setBackgroundResource(R.drawable.border_encounter);
                    break;
                case DAILY:
                    freq = "Daily";
                    convertView.setBackgroundResource(R.drawable.border_daily);
                    break;
            }
        }

        // Credit For Damage Type Images: http://imgur.com/NRRduuJ
        ImageView imageView = (ImageView) convertView.findViewById(R.id.Power_DamageType);
        DamageType damageType = power.getDamageType();
        if (damageType != null) {
            switch (damageType) {
                case SLASHING:
                    imageView.setImageResource(R.drawable.type_slashing);
                    break;
                case BLUDGEONING:
                    imageView.setImageResource(R.drawable.type_bludgeoning);
                    break;
                case PIERCING:
                    imageView.setImageResource(R.drawable.type_piercing);
                    break;
                case FORCE:
                    imageView.setImageResource(R.drawable.type_force);
                    break;
                case FIRE:
                    imageView.setImageResource(R.drawable.type_fire);
                    break;
                case COLD:
                    imageView.setImageResource(R.drawable.type_cold);
                    break;
                case LIGHTNING:
                    imageView.setImageResource(R.drawable.type_lightning);
                    break;
                case THUNDER:
                    imageView.setImageResource(R.drawable.type_thunder);
                    break;
                case POISON:
                    imageView.setImageResource(R.drawable.type_poison);
                    break;
                case ACID:
                    imageView.setImageResource(R.drawable.type_acid);
                    break;
                case PSYCHIC:
                    imageView.setImageResource(R.drawable.type_psychic);
                    break;
                case NECROTIC:
                    imageView.setImageResource(R.drawable.type_necrotic);
                    break;
                case RADIANT:
                    imageView.setImageResource(R.drawable.type_radiant);
                    break;
            }
        }

        ((TextView) convertView.findViewById(R.id.Power_Freq)).setText(freq);

        return convertView;
    }

    @Override
    public int getCount() {
        return powerList.size();
    }

    void add(Power item) {
        this.powerList.add(item);
    }

    void addAll(ArrayList<Power> items) {
        this.powerList.addAll(items);
    }
}
