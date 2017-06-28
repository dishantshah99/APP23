package com.example.user.app23;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class img_adpter extends PagerAdapter {
    private Context context;
    FragmentManager manager;
    ArrayList<Setget_benner> list;
    private LayoutInflater layoutInflater;
    private int[] img = {R.drawable.h, R.drawable.a1, R.drawable.o, R.drawable.l, R.drawable.m, R.drawable.i, R.drawable.d};


    public img_adpter(Context c) {
        context = c;
    }

    public img_adpter(FragmentActivity activity, ArrayList<Setget_benner> setget_benner, FragmentManager manager) {
        this.context = activity;
        this.list = setget_benner;
        this.manager = manager;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = layoutInflater.inflate(R.layout.custom_imgview, container, false);
        ImageView imageView = (ImageView) itemview.findViewById(R.id.swipe_iv);
        imageView.setImageResource(img[position]);
       /* Picasso.with(context).load(list.get(position).getB_img()).placeholder(R.drawable.ahahaha).into(imageView);*/
        container.addView(itemview);
        return itemview;
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
