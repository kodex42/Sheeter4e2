package com.sheeter.azuris.sheeter4e;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.ActionType;
import com.sheeter.azuris.sheeter4e.Modules.DamageType;
import com.sheeter.azuris.sheeter4e.Modules.Frequency;
import com.sheeter.azuris.sheeter4e.Modules.Power;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-21.
 */

public class PowerSummaryFragment extends Fragment {
    private Power mPower;

    PowerSummaryFragment(Power power) {
        super();
        mPower = power;
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
        String powerName = mPower.getName();
        ArrayList<Power> powers = MainActivity.sCharacter.sheet.getPowers();

        for (int i = 0; i < powers.size(); i++) {
            if (powers.get(i).getName().equals(powerName)) {
                mPower = powers.get(i);
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
        ((ImageView)root.findViewById(R.id.PowerModalActivity_ImageView_DamageType)).setImageResource(DamageType.getImageId(mPower.getDamageType()));
        ((TextView)root.findViewById(R.id.PowerModalActivity_TextView_ActionType)).setText(ActionType.getRaw(mPower.getActionType()));
        ((TextView)root.findViewById(R.id.PowerModalActivity_TextView_Frequency)).setText(Frequency.getRaw(mPower.getFrequency()));
    }
}
