package com.sheeter.azuris.sheeter4e;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sheeter.azuris.sheeter4e.Modules.D20Class;
import com.sheeter.azuris.sheeter4e.Modules.D20Race;

import java.util.ArrayList;

public class BuilderActivity extends AppCompatActivity {
    public static BuilderActivity sBuilderActivity;
    public static ArrayList<D20Race> sAvailableRaces;
    public static ArrayList<D20Class> sAvailableClasses;

    private BottomNavigationView mNavigationView;
    private BuilderActivity.FragmentAdapter mFragmentAdapter;
    private ScrollLockViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sBuilderActivity = this;
        setContentView(R.layout.activity_builder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationView = findViewById(R.id.Builder_Navigation);
        mViewPager = findViewById(R.id.Builder_Pager);

        // ViewPager and its adapters use support library fragments, so use getSupportFragmentManager.
        mFragmentAdapter =
                new BuilderActivity.FragmentAdapter(
                        getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        if (position == 0) {
                            mNavigationView.setSelectedItemId(R.id.action_home);
                        }
                        else if (position == 1) {
                            mNavigationView.setSelectedItemId(R.id.action_powers);
                            PowersFragment.refreshFragment(mViewPager.getChildAt(position), BuilderActivity.this);
                        }
                    }
                });
    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
    // and NOT a FragmentPagerAdapter.
    private class FragmentAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments;

        FragmentAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[1];
            fragments[0] = new RacePickerFragment();
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
