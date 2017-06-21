package com.sheeter.azuris.sheeter4e;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.ActionType;
import com.sheeter.azuris.sheeter4e.Modules.DamageType;
import com.sheeter.azuris.sheeter4e.Modules.Frequency;
import com.sheeter.azuris.sheeter4e.Modules.Power;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-20.
 */

public class PowerModalActivity extends AppCompatActivity {
    private Power mPower = null;
    public static final String EXTRA_KEY_POWER_NAME = "powerName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_summary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String powerName = getIntent().getExtras().getString(EXTRA_KEY_POWER_NAME);
        ArrayList<Power> powers = MainActivity.sCharacter.sheet.getPowers();

        for (int i = 0; i < powers.size(); i++) {
            if (powers.get(i).getName().equals(powerName)) {
                mPower = powers.get(i);
                break;
            }
        }

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
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
        findViewById(R.id.PowerModalActivity_LinearLayout_Background).setBackgroundColor(getResources().getColor(colorId));
        findViewById(R.id.PowerModalActivity_LinearLayout_PowerNameBackground).setBackgroundColor(getResources().getColor(colorId));
        ((TextView)findViewById(R.id.PowerModalActivity_TextView_Frequency)).setTextColor(getResources().getColor(colorId));

        // Data
        ((TextView)findViewById(R.id.PowerModalActivity_TextView_PowerName)).setText(mPower.getName());
        ((ImageView)findViewById(R.id.PowerModalActivity_ImageView_DamageType)).setImageResource(DamageType.getImageId(mPower.getDamageType()));
        ((TextView)findViewById(R.id.PowerModalActivity_TextView_ActionType)).setText(ActionType.getRaw(mPower.getActionType()));
        ((TextView)findViewById(R.id.PowerModalActivity_TextView_Frequency)).setText(Frequency.getRaw(mPower.getFrequency()));
    }
}
