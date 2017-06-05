package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sheeter.azuris.sheeter4e.Modules.Power;

import java.util.List;

/**
 * Created by Azuris on 2017-06-05.
 */

public class PowersListViewAdapter extends ArrayAdapter<String> {
    List<Power> powerList;
    Context context;

    public PowersListViewAdapter(Context context, List<Power> powers) {
        super(context, R.layout.power_row);
        this.context = context;
        this.powerList = powers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Power power = this.powerList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.power_row, parent, false);

        
    }
}
