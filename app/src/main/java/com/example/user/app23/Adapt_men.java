package com.example.user.app23;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class Adapt_men extends RecyclerView.Adapter<Adapt_men.Myviewholder> {
    Context context;
    ArrayList<Setget_men> list;
    FragmentTransaction transaction;
    FragmentManager manager;
    Fragment fragment;

    public Adapt_men(FragmentActivity   activity, ArrayList<Setget_men> setget_men, FragmentManager manager) {
        this.context = activity;
        this.list = setget_men;
        this.manager = manager;
    }

    @Override
    public Adapt_men.Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product, null);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(Adapt_men.Myviewholder holder, final int position) {
        holder.pro_title.setText(list.get(position).getSubproduct_name());
        holder.pro_price.setText(list.get(position).getSubproduct_price());
        Picasso.with(context).load(list.get(position).getSubproduct_image()).placeholder(R.drawable.app23_logo).into(holder.pro_img);
        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = list.get(position).getSubproduct_id();
                fragment = new Product_desc(context, list, s1, position);
                transaction = manager.beginTransaction();
                transaction.replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public ImageView pro_img;
        public TextView pro_price, pro_title;
        public Button btn_view;

        public Myviewholder(View itemView) {
            super(itemView);
            pro_img = (ImageView) itemView.findViewById(R.id.pro_img);
            pro_title = (TextView) itemView.findViewById(R.id.pro_title);
            pro_price = (TextView) itemView.findViewById(R.id.pro_price);
            btn_view = (Button) itemView.findViewById(R.id.btn_view);
        }
    }
}
