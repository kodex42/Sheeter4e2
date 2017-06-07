package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sheeter.azuris.sheeter4e.Modules.DamageType;
import com.sheeter.azuris.sheeter4e.Modules.Frequency;
import com.sheeter.azuris.sheeter4e.Modules.Power;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Power power = this.powerList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.power_row, null);

        ((TextView) convertView.findViewById(R.id.Power_Name)).setText(power.getName());

        // Credit For Damage Type Images: http://imgur.com/NRRduuJ
        ImageView imageView = (ImageView) convertView.findViewById(R.id.Power_DamageType);
        DamageType damageType = power.getDamageType();
        if (damageType != null) {
            switch (damageType) {
                case SLASHING:
                    imageView.setImageResource(R.drawable.type_slashing_framed);
                    break;
                case BLUDGEONING:
                    imageView.setImageResource(R.drawable.type_bludgeoning_framed);
                    break;
                case PIERCING:
                    imageView.setImageResource(R.drawable.type_piercing_framed);
                    break;
                case PHYSICAL:
                    imageView.setImageResource(R.drawable.type_slashing_framed);
                    break;
                case FORCE:
                    imageView.setImageResource(R.drawable.type_force_framed);
                    break;
                case FIRE:
                    imageView.setImageResource(R.drawable.type_fire_framed);
                    break;
                case COLD:
                    imageView.setImageResource(R.drawable.type_cold_framed);
                    break;
                case LIGHTNING:
                    imageView.setImageResource(R.drawable.type_lightning_framed);
                    break;
                case THUNDER:
                    imageView.setImageResource(R.drawable.type_thunder_framed);
                    break;
                case POISON:
                    imageView.setImageResource(R.drawable.type_poison_framed);
                    break;
                case ACID:
                    imageView.setImageResource(R.drawable.type_acid_framed);
                    break;
                case PSYCHIC:
                    imageView.setImageResource(R.drawable.type_psychic_framed);
                    break;
                case NECROTIC:
                    imageView.setImageResource(R.drawable.type_necrotic_framed);
                    break;
                case RADIANT:
                    imageView.setImageResource(R.drawable.type_radiant_framed);
                    break;
            }
        }

        Frequency frequency = power.getFrequency();
        String freq = null;
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
        ((TextView) convertView.findViewById(R.id.Power_Freq)).setText(freq);

        // This is to ensure the view keeps a similar state when refreshed.
        if (power.isCasted()) {
            convertView.setBackgroundColor(Color.parseColor("#88808080"));
        }
        else {
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle(power.getName())
                            .setPositiveButton("Cast", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    power.cast();
                                    Toast.makeText(context, "Casted", Toast.LENGTH_SHORT).show();
                                    finalConvertView.setBackgroundColor(Color.parseColor("#88808080"));
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }
            });
        }

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

    void refreshPowers(int clearance) {
        ArrayList<Frequency> frequencies = new ArrayList<>();

        switch (clearance) {
            case 2:
                frequencies.add(Frequency.DAILY);
            case 1:
                frequencies.add(Frequency.ENCOUNTER);
            case 0:
                frequencies.add(Frequency.AT_WILL);
        }

        for (Power power:powerList) {
            if (frequencies.contains(Frequency.AT_WILL) && power.getFrequency() == Frequency.AT_WILL)
                power.refresh();

            if (frequencies.contains(Frequency.ENCOUNTER) && power.getFrequency() == Frequency.ENCOUNTER || power.getFrequency() == Frequency.ENCOUNTER_SPECIAL)
                power.refresh();

            if (frequencies.contains(Frequency.DAILY) && power.getFrequency() == Frequency.DAILY)
                power.refresh();
        }

        notifyDataSetChanged();
    }
}
