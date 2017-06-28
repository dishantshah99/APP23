package com.example.user.app23;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.app23.Constant.ConnectionDetector;
import com.example.user.app23.Constant.Constant;
import com.example.user.app23.Constant.MakeServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart extends AppCompatActivity {
    private static final String TOTAL_PRICE = "200";
    private static final String CART_NUMBER = "0";
    ArrayList<Setget_cart> setget_cart;
    RecyclerView cart_lv;
    TextView cart_foprice, gohome;
    Handler mhandler;
    private SQLiteDatabase db;
    String semail, stitle, simag, spay, sprice, sqty, sfoprice, user_id, stotal, scart_count;
    Adapt_cart adapt_cart;
    SharedPreferences sp;
    Button cart_chackout, payment_btn;
    FragmentManager manager;
    Context context = this;
    TextView cart_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart_number = (TextView) findViewById(R.id.cart_number);

        getSupportActionBar().hide();
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView edit = (TextView) findViewById(R.id.edit_txt);
        EditText promo = (EditText) findViewById(R.id.promo_edit);
        cart_foprice = (TextView) findViewById(R.id.cart_foprice);
        payment_btn = (Button) findViewById(R.id.payment_btn);
        promo.setFocusable(false);
        edit.setFocusable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sp = getSharedPreferences(Constant.PER, MODE_PRIVATE);
        user_id = sp.getString(Constant.USER_ID, null);
        Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_SHORT).show();
        cart_lv = (RecyclerView) findViewById(R.id.cart_rv);
        setget_cart = new ArrayList<>();
        manager = getSupportFragmentManager();
        if (new ConnectionDetector(context).isConnectingToInternet()) {
            cart_lv.setLayoutManager(new GridLayoutManager(context, 1));
            cart_lv.setItemAnimator(new DefaultItemAnimator());
            setget_cart = new ArrayList<>();
            manager = getSupportFragmentManager();
            user_id = sp.getString(Constant.USER_ID, null);
            Toast.makeText(context, user_id, Toast.LENGTH_SHORT).show();
            new getcartdata().execute();
            new getcartcount().execute();
        } else {
            new ConnectionDetector(context).connectiondetect();
        }

        //    cart_foprice = (TextView)findViewById(R.id.cart_foprice);


        //   cart_chackout = (Button)findViewById(R.id.cart_chackout);

       /* ArrayList<Setget_cart> carts = new ArrayList<Setget_cart>();
        carts.clear();
        String query = "Select * from cart ";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1.moveToFirst()) {
            do {
                Setget_cart contactListItems = new Setget_cart();

                contactListItems.setTitle(c1.getString(c1
                        .getColumnIndex("title")));
                contactListItems.setPrice(c1.getString(c1
                        .getColumnIndex("price")));
                contactListItems.setQry(c1.getString(c1
                        .getColumnIndex("qty")));
                contactListItems.setPro_price(c1.getString(c1
                        .getColumnIndex("pro_price")));
                carts.add(contactListItems);

            } while (c1.moveToNext());
        }
        c1.close();
        Adapt_cart adaptCart = new Adapt_cart(getContext(), carts, manager);
        cart_lv.setAdapter(adaptCart);*/


       /* cart_rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        cart_rv.setItemAnimator(new DefaultItemAnimator());*/

      /*  sfoprice = cart_foprice.getText().toString();*/
       /* String show_query = "select * from cart";
        Cursor c = db.rawQuery(show_query, null);
        StringBuilder sb = new StringBuilder();
        if (c.moveToFirst()) {
            do {
                sb.append(c.getString(c.getColumnIndex("title")) + "\t");
                sb.append(c.getString(c.getColumnIndex("price")) + "\t");
                sb.append(c.getString(c.getColumnIndex("payment")) + "\t");
                sb.append(c.getString(c.getColumnIndex("image")) + "\t");
                sb.append(c.getString(c.getColumnIndex("qty")) + "\n");
            }
            while (c.moveToNext()) ;
            {
                stitle = c.getString(0);
                sprice = c.getString(1);
                spay = c.getString(2);
                simag = c.getString(3);
                sqty = c.getString(4);

                Setget_cart list = new Setget_cart();
                list.setTitle(stitle);
                list.setPrice(sprice);
                list.setPayment(spay);
                list.setImage(simag);
                list.setQry(sqty);
                setget_cart.add(list);
            }
            adapt_cart = new Adapt_cart(getActivity(), setget_cart);
            cart_rv.setAdapter(adapt_cart);
        }else {
            Toast.makeText(context, "False", Toast.LENGTH_SHORT).show();
        }*/


        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Address_user.class));
            }
        });
    }


    private class getcartdata extends AsyncTask<String, String, String> {
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
            return new MakeServiceCall().MakeServiceCall(Constant.SAMANTA_IP + "getCartDetails.php?action=getCartDetails&user_master_id=" + user_id, MakeServiceCall.GET);

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
                    JSONArray jsonArray = object.getJSONArray("response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Setget_cart list = new Setget_cart();
                        list.setTitle(jsonObject.getString("subproduct_master_name"));
                        list.setImage(jsonObject.getString("subproduct_master_image"));
                        list.setPrice(jsonObject.getString("subproduct_master_price"));
                        list.setTotal(jsonObject.getString("total_price"));
                        list.setCart_id(jsonObject.getString("cart_details_id"));
                        list.setProduct_master_id(jsonObject.getString("product_master_id"));
                        list.setSubproduct_master_id(jsonObject.getString("subproduct_master_id"));
                        list.setQry(jsonObject.getString("quantity"));
                        list.setSize(jsonObject.getString("size_master_code"));
                        list.setQry(jsonObject.getString("quantity"));
                        setget_cart.add(list);
                    }
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put(TOTAL_PRICE, object.getString("total_amount"));
                    stotal = data.get(TOTAL_PRICE).toString();
                    cart_foprice.setText(stotal);
                    //   Toast.makeText(getActivity(), stotal, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
                adapt_cart = new Adapt_cart(context, setget_cart, manager);
                cart_lv.setAdapter(adapt_cart);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getcartcount extends AsyncTask<String, String, String> {
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
            return new MakeServiceCall().MakeServiceCall(Constant.SAMANTA_IP + "getCartDetails.php?action=getCartDetails&user_master_id=" + user_id, MakeServiceCall.GET);
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
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put(CART_NUMBER, object.getString("total_cart_item"));
                    scart_count = data.get(CART_NUMBER).toString();
                    Toast.makeText(context, scart_count, Toast.LENGTH_SHORT).show();
                    cart_number.setText(scart_count);
                } else {
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
