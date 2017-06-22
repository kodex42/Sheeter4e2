package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Power power = this.powerList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.power_row, null);

        ((TextView) convertView.findViewById(R.id.Power_Name)).setText(power.getName());

        // Credit For Damage Type Images: http://imgur.com/NRRduuJ
        ImageView imageView = (ImageView) convertView.findViewById(R.id.Power_DamageType);
        DamageType damageType = power.getDamageType();
        if (damageType != null) {
            imageView.setImageResource(DamageType.getImageId(damageType));
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
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setIcon(DamageType.getImageId(power.getDamageType()))
                            .setTitle(power.getName())
                            .setPositiveButton("Summary", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, PowerSummaryActivity.class);
                                    intent.putExtra(PowerSummaryActivity.EXTRA_KEY_POWER_INDEX, position);
                                    context.startActivity(intent);
                                }
                            })
                            .show();
                }
            });
        }
        else {
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setIcon(DamageType.getImageId(power.getDamageType()))
                            .setTitle(power.getName())
                            .setNegativeButton("Cast", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    power.cast();
                                    Toast.makeText(context, "Casted", Toast.LENGTH_SHORT).show();
                                    finalConvertView.setBackgroundColor(Color.parseColor("#88808080"));
                                    dialogInterface.dismiss();
                                    finalConvertView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new AlertDialog.Builder(context)
                                                    .setIcon(DamageType.getImageId(power.getDamageType()))
                                                    .setTitle(power.getName())
                                                    .setPositiveButton("Summary", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            Intent intent = new Intent(context, PowerSummaryActivity.class);
                                                            intent.putExtra(PowerSummaryActivity.EXTRA_KEY_POWER_INDEX, position);
                                                            context.startActivity(intent);
                                                        }
                                                    })
                                                    .show();
                                        }
                                    });
                                }
                            })
                            .setPositiveButton("Summary", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context, PowerSummaryActivity.class);
                                    intent.putExtra(PowerSummaryActivity.EXTRA_KEY_POWER_INDEX, position);
                                    context.startActivity(intent);
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
        this.powerList.clear();
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
