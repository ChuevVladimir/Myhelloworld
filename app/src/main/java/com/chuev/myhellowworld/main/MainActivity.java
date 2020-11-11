package com.chuev.myhellowworld.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.widget.Toolbar;

import com.chuev.myhellowworld.AddItemActivity;
import com.chuev.myhellowworld.BudgetFragment;
import com.chuev.myhellowworld.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

private BudgetPagerAdapter pagerAdapter;
    public static final String EXPENSE = "expense";
    public static final String INCOME = "income";
    private TabLayout tabLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        tabLayout = findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar);
        ViewPager viewPager = findViewById(R.id.fragment_ViewPager);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v -> {
            final int activeFragmenindex=viewPager.getCurrentItem();
            Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmenindex);
            Intent newActivity = new Intent(MainActivity.this, AddItemActivity.class);
            activeFragment.startActivityForResult(newActivity, BudgetFragment.REQUEST_CODE);
        });

        pagerAdapter = new BudgetPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);



        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.rashod);

        tabLayout.getTabAt(1).setText(R.string.dohod);


        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BudgetFragment) {
                ((BudgetFragment)fragment).generate_content();
            }
        }

    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_gray));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_gray));
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {

        super.onActionModeFinished(mode);

        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.color_Primary));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.color_Primary));
    }

    private class BudgetPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> fragments = new ArrayList<>();
        public BudgetPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return BudgetFragment.newInstance(R.color.color_Primary, EXPENSE);
                case 1:
                    return BudgetFragment.newInstance(R.color.apple_green, INCOME);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        void addFragment(Fragment fragment) {
            fragments.add(fragment);

        }
    }



}