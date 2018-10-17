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
import android.widget.ProgressBar;

public class BuilderActivity extends AppCompatActivity {
    private ProgressBar sProgressBar;
    private BottomNavigationView mNavigationView;
    private BuilderActivity.FragmentAdapter mFragmentAdapter;
    private ScrollLockViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sProgressBar = findViewById(R.id.Builder_Progressbar);
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
