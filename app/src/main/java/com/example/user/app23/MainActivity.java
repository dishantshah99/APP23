package com.example.user.app23;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, Login.OnFragmentInteractionListener1, Signup.OnFragmentInteractionListener2 {
    ViewPager mViewPager;
    ActionBar mActionBar;
    SectionPageAdapter mSectionPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        mViewPager = (ViewPager) findViewById(R.id.viewPager1);
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionPageAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);
                mActionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < mSectionPageAdapter.getCount(); i++) {
            mActionBar.addTab(mActionBar.newTab().setText(mSectionPageAdapter.getPageTitle(i)).setTabListener(MainActivity.this));
        }


    }

    @Override
    public void onFragmentInteraction1(Uri uri) {

    }



    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    public class SectionPageAdapter extends FragmentPagerAdapter {

        public SectionPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Login.newInstance("Hello", "This is fragment1.");
                case 1:
                    return Signup.newInstance("Hello", "This is fragment2.");
                default:
                    return Login.newInstance("Hello", "This is fragment1.");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "LOGIN";
                case 1:
                    return "SIGNUP";
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will

        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
