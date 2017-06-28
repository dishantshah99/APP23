package com.example.user.app23;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.TimerTask;


public class HomeFragment extends Fragment {
    private View parentView;
    ViewPager viewPager;
    img_adpter Img_adpter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_homepage, container, false);
        //setUpViews();
        viewPager = (ViewPager) parentView.findViewById(R.id.img_view);
        Img_adpter = new img_adpter(getContext());
        viewPager.setAdapter(Img_adpter);


        Button btn_buy = (Button) parentView.findViewById(R.id.btn_buy);
       /* btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Product_desc();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.main_fragment, fragment).commit();
            }
        });*/
        /*Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeFragment.MyTimeTask(), 1000, 2000);*/
        return parentView;
    }

    private class MyTimeTask extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(4);
                    } else if (viewPager.getCurrentItem() == 4) {
                        viewPager.setCurrentItem(5);
                    } else if (viewPager.getCurrentItem() == 5) {
                        viewPager.setCurrentItem(6);
                    } else if (viewPager.getCurrentItem() == 6) {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}




