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
        View view = inflater.inflate(R.layout.power_row, parent, false);

        ((TextView) view.findViewById(R.id.Power_Name)).setText(power.getName());

        String freq = null;
        Frequency frequency = power.getFrequency();
        switch (frequency) {
            case AT_WILL:
                freq = "At-Will";
                view.setBackgroundColor(context.getResources().getColor(R.color.atWillPower));
                break;
            case ENCOUNTER:
                freq = "Encounter";
                view.setBackgroundColor(context.getResources().getColor(R.color.encounterPower));
                break;
            case DAILY:
                freq = "Daily";
                view.setBackgroundColor(context.getResources().getColor(R.color.dailyPower));
        }

        ((TextView) view.findViewById(R.id.Power_Freq)).setText(freq);

        return view;
    }

    void add(Power item) {
        this.powerList.add(item);
    }
}
