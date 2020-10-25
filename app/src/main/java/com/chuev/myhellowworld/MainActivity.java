package com.chuev.myhellowworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import android.text.Layout;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

private BudgetPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        TabLayout tabLayout = findViewById(R.id.tabs);


        ViewPager viewPager = findViewById(R.id.fragment_ViewPager);


        pagerAdapter = new BudgetPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(BudgetFragment.newInstance("синий"));
        pagerAdapter.addFragment(BudgetFragment.newInstance("красный"));


        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.rashod);

        tabLayout.getTabAt(1).setText(R.string.dohod);


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
            return  fragments.get(position);
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