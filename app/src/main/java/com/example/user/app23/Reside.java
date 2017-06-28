package com.example.user.app23;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;


public class Reside extends AppCompatActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemmen;
    private ResideMenuItem itemwomen;
    private ResideMenuItem itemkidsmen;
    private ResideMenuItem itemkidswomen;
    private ResideMenuItem itemmyaccount;
    private ResideMenuItem itemhistory;
    private ResideMenuItem itemAbout;
    private ResideMenuItem itemSupport;
    private ResideMenuItem itemTermsofService;
    private ResideMenuItem itemlogout;
    ResideMenu itemname;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment = new HomeFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.main_fragment, fragment).commit();
                    return true;
                case R.id.navigation_cart:
                    startActivity(new Intent(getApplicationContext(), Cart.class));
                    return true;
                case R.id.navigation_notifications:
                    fragment = new WomenFragment();
                    manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.main_fragment, fragment).commit();
                    return true;
                default:
                    fragment = new HomeFragment();
                    manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.main_fragment, fragment).commit();
                    return true;
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_reside);
        getSupportActionBar().hide();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ImageView search_iv = (ImageView) findViewById(R.id.search_iv);

        search_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Search.class));
            }
        });
        mContext = this;
        setUpMenu();

        if (savedInstanceState == null)
            changeFragment(new HomeFragment());
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);

        resideMenu.setBackground(R.color.colorNav);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);
        // create menu items;

        itemHome = new ResideMenuItem(this, R.drawable.home_icon, "Home");
        itemmen = new ResideMenuItem(this, R.drawable.profile_icon, "Men");
        itemwomen = new ResideMenuItem(this, R.drawable.home_icon, "Women");
        itemkidsmen = new ResideMenuItem(this, R.drawable.profile_icon, "Baby Boy");
        itemkidswomen = new ResideMenuItem(this, R.drawable.profile_icon, "Baby girl");
        itemmyaccount = new ResideMenuItem(this, R.drawable.home_icon, "My Account");
        itemhistory = new ResideMenuItem(this, R.drawable.profile_icon, "History");
        itemAbout = new ResideMenuItem(this, R.drawable.home_icon, "About us");
        itemSupport = new ResideMenuItem(this, R.drawable.profile_icon, "Support");
        itemTermsofService = new ResideMenuItem(this, R.drawable.home_icon, "Terms of Service");
        itemlogout = new ResideMenuItem(this, R.color.colorNav, "Logout");

        itemHome.setOnClickListener(this);
        itemmen.setOnClickListener(this);
        itemwomen.setOnClickListener(this);
        itemkidsmen.setOnClickListener(this);
        itemkidswomen.setOnClickListener(this);
        itemmyaccount.setOnClickListener(this);
        itemhistory.setOnClickListener(this);
        itemAbout.setOnClickListener(this);
        itemSupport.setOnClickListener(this);
        itemTermsofService.setOnClickListener(this);
        itemlogout.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemmen, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemwomen, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemkidsmen, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemkidswomen, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemmyaccount, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemhistory, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSupport, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemTermsofService, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemlogout, ResideMenu.DIRECTION_LEFT);


        // You can disable a direction by setting ->
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome) {
            changeFragment(new HomeFragment());
        } else if (view == itemmen) {
            changeFragment(new MenFragment());
        } else if (view == itemwomen) {
            changeFragment(new WomenFragment());
        } else if (view == itemkidsmen) {
            changeFragment(new KidsFragment());
        } else if (view == itemkidswomen) {
            changeFragment(new KidsFragment());
        } else if (view == itemmyaccount) {
            changeFragment(new Account());
        }else if (view == itemhistory) {
            changeFragment(new Order_history());
        }
        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {

        }
    };

    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }
}