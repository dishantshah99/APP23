package com.example.user.app23;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.app23.Constant.ConnectionDetector;
import com.example.user.app23.Constant.Constant;
import com.example.user.app23.Constant.MakeServiceCall;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


class Adapt_cart extends RecyclerView.Adapter<Adapt_cart.Myviewholder> {
    Context context;
    ArrayList<Setget_cart> list;
    FragmentTransaction transaction;
    FragmentManager manager;
    Fragment fragment;
    String cart_id;
    Integer count = 1;

    public Adapt_cart(Context context, ArrayList<Setget_cart> setget_cart, FragmentManager manager) {
        this.context = context;
        this.list = setget_cart;
        this.manager = manager;
    }


    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_cart, null);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(final Myviewholder holder, final int position) {
        holder.cart_title.setText(list.get(position).getTitle());
        holder.cart_price.setText(list.get(position).getPrice());
        holder.totle_price.setText(list.get(position).getTotal());
        holder.cart_qty_txt.setText(list.get(position).getQry());
        holder.cart_size.setText(list.get(position).getSize());
        Picasso.with(context).load(list.get(position).getImage()).placeholder(R.drawable.k).into(holder.cart_img);
        holder.cart_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new ConnectionDetector(context).isConnectingToInternet()) {
                    cart_id = list.get(position).cart_id;
                    Toast.makeText(context, cart_id, Toast.LENGTH_SHORT).show();
                    new deletecart().execute();
                } else {
                    new ConnectionDetector(context).connectiondetect();
                }
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1 = (Integer.parseInt(holder.cart_qty_txt.getText().toString()));
                if (s1 <= 2) {
                    holder.minus.setEnabled(true);
                }
                count = count + 1;
                display(count);
                double e1 = Double.parseDouble(holder.cart_price.getText().toString());
                double e2 = Double.parseDouble(holder.cart_qty_txt.getText().toString());
                double tmmad = (e1 * e2);
                holder.totle_price.setText(Double.toString(tmmad));
            }

            private void display(Integer count) {
                holder.cart_qty_txt.setText("" + count);
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1 = (Integer.parseInt(holder.cart_qty_txt.getText().toString()));
                if (s1 == 1) {
                    holder.minus.setEnabled(false);
                } else {
                    count = count - 1;
                    display(count);
                }


                double e1 = Double.parseDouble(holder.cart_price.getText().toString());
                double e2 = Double.parseDouble(holder.cart_qty_txt.getText().toString());
                double tmmad = (e1 * e2);
                holder.totle_price.setText(Double.toString(tmmad));
            }

            private void display(Integer count) {
                holder.cart_qty_txt.setText("" + count);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView cart_title, cart_price, totle_price;
        TextView cart_qty_txt, cart_size;
        ImageView cart_img;
        TextView cart_delete;
        ImageView plus, minus;

        public Myviewholder(View itemView) {
            super(itemView);
            cart_title = (TextView) itemView.findViewById(R.id.cart_title);
            cart_price = (TextView) itemView.findViewById(R.id.cart_price);
            totle_price = (TextView) itemView.findViewById(R.id.cart_total);
            cart_img = (ImageView) itemView.findViewById(R.id.cart_img);
            cart_qty_txt = (TextView) itemView.findViewById(R.id.qty_txt);
            cart_size = (TextView) itemView.findViewById(R.id.txt_size);
            cart_delete = (TextView) itemView.findViewById(R.id.cart_delet);
            plus = (ImageView) itemView.findViewById(R.id.plus);
            minus = (ImageView) itemView.findViewById(R.id.minus);
        }
    }

    private class deletecart extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setMessage("Loading...");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return new MakeServiceCall().MakeServiceCall(Constant.SAMANTA_IP + "deleteCartDetails.php?action=deleteCartDetails&cart_details_id=" + cart_id, MakeServiceCall.GET);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("Status").equalsIgnoreCase("1")) {
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, Cart.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
