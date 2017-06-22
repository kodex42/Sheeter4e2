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
                fragments[i] = new SummaryFragment(mPowers.get(i));
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
