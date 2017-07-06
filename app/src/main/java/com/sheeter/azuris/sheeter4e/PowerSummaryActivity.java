package com.sheeter.azuris.sheeter4e;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sheeter.azuris.sheeter4e.Modules.Power;
import com.sheeter.azuris.sheeter4e.SQLITE.Column;
import com.sheeter.azuris.sheeter4e.SQLITE.FeedReaderContract;

import java.util.ArrayList;

/**
 * Created by Azuris on 2017-06-20.
 */

public class PowerSummaryActivity extends AppCompatActivity {
    public static final String EXTRA_KEY_POWER_INDEX = "powerIndex";
    private Power mPower = null;
    private ViewPager mViewPager;
    private ArrayList<Power> mPowers;
    private int mPowerIndex;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_summary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPowers = MainActivity.sCharacter.sheet.getPowers();

        // TODO: Fix issues with certain powers not in database
        for (Power power : mPowers) {
            ArrayList powerMaps = MainActivity.sDbHelper.query(power.getName());
            if (powerMaps.size() > 0) {
                for (Object obj : powerMaps) {
                    Column column = (Column) obj;

                    switch (column.getColumnName()) {
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_NAME:
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_REQUIREMENT:
                            power.setRequirement(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_TRIGGER:
                            power.setTrigger(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_SUSTAIN_ACTION:
                            power.setSustainAction(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_WEAPON_BONUS:
                            power.setWeaponBonus(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_PRIMARY_TARGET:
                            power.setPrimaryTarget(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_TARGET:
                            power.setSecondaryTarget(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_ATTACK:
                            power.setSecondaryAttack(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_SECONDARY_HIT:
                            power.setSecondaryHit(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_TARGET:
                            power.setTertiaryTarget(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_ATTACK:
                            power.setTertiaryAttack(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_TERTIARY_HIT:
                            power.setTertiaryHit(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_RANGE:
                            power.setRange(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_HIT_EFFECTS:
                            power.setHitEffects(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_MISS_EFFECTS:
                            power.setMissEffects(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_EFFECTS:
                            power.setEffects(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_SPECIAL_EFFECTS:
                            power.setSpecialEffects(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_11:
                            power.setDamageIncreaseAt11(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_DAMAGE_INCREASE_AT_21:
                            power.setDamageIncreaseAt21(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_PARAGON:
                            power.setParagon(column.getValue());
                            break;
                        case FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION:
                            power.setDescription(column.getValue());
                            break;
                    }

                    power.setWasQueried(true);
                }
            }
        }

        mPowerIndex = getIntent().getExtras().getInt(EXTRA_KEY_POWER_INDEX);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mFragmentAdapter =
                new PowerSummaryActivity.FragmentAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.Summary_Pager);
        mViewPager.setAdapter(mFragmentAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mViewPager, true);

        mViewPager.setCurrentItem(mPowerIndex);
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

    // Since this is an object collection, use a FragmentStatePagerAdapter,
    // and NOT a FragmentPagerAdapter.
    private class FragmentAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments;

        FragmentAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[mPowers.size()];

            for (int i = 0; i < mPowers.size(); i++) {
                fragments[i] = new PowerSummaryFragment(mPowers.get(i));
            }
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
