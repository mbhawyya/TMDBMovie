package com.bhawyyamittal.tabbedactivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        tabLayout = findViewById(R.id.tab_layout);
        //tabLayout.
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class MyPagerAdapter extends FragmentStatePagerAdapter{
    MyPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Top Rated Movies";
            //break;
            case 1 :
                return "Popular Movies";
            //   break;
            case 2 :
                return "IMDB Top 250";

        }
        return "Tab "+position;
    }

    @Override
    public Fragment getItem(int position) {
        FragmentOne f1 = FragmentOne.newInstance(position+1);
        FragmentTwo  f2 = FragmentTwo.newInstance(position+1);
        FragmentThree f3 = FragmentThree.newInstance(position+1);
        switch (position){
            case 0 :
                return f1;
                //break;
            case 1 :
                return  f2;
             //   break;
            case  2 :
                return f3;

        }
        return f2;


    }

    @Override
    public int getCount() {
        return 3;
    }
}
