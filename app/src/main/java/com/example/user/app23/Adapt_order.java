package com.example.user.app23;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class Adapt_order extends RecyclerView.Adapter<Adapt_order.Myviewholder> {
    Context context;
    ArrayList<Setget_order> list;
    FragmentTransaction transaction;
    FragmentManager manager;

    public Adapt_order(FragmentActivity activity, ArrayList<Setget_order> setget_order, FragmentManager manager) {
        this.context = activity;
        this.list = setget_order;
        this.manager = manager;
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order, null);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {
        holder.order_name.setText(list.get(position).getOrder_name());
        holder.order_price.setText(list.get(position).getOrder_price());
        holder.order_qty.setText(list.get(position).getOrder_qty());
        holder.order_status.setText(list.get(position).getOrder_status());
        Picasso.with(context).load(list.get(position).getOrder_image()).placeholder(R.drawable.k).into(holder.order_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView order_name, order_price, order_status, order_qty;
        ImageView order_img;

        public Myviewholder(View itemView) {
            super(itemView);
            order_name = (TextView) itemView.findViewById(R.id.order_name);
            order_price = (TextView) itemView.findViewById(R.id.order_price);
            order_status = (TextView) itemView.findViewById(R.id.order_status);
            order_qty = (TextView) itemView.findViewById(R.id.order_qty);
            order_img = (ImageView) itemView.findViewById(R.id.order_img);
        }
    }
}
