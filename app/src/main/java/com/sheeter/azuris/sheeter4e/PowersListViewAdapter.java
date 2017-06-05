package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
                case DAILY:
                    freq = "Daily";
                    convertView.setBackgroundResource(R.drawable.border_daily);
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
