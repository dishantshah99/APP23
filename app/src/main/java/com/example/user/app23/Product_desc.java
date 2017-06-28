package com.example.user.app23;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.app23.Constant.ConnectionDetector;
import com.example.user.app23.Constant.Constant;
import com.example.user.app23.Constant.MakeServiceCall;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Product_desc extends Fragment {
    private static final String CART_NUMBER = "0";
    TextView pro_title, price, pay_txt, desc_txt, legal_txt, type_txt, weight_txt, fo_price, qty_txt, qty;
    Spinner clothsize;
    ImageView img1, veg_img, plus, minus;
    Button cart_btn, show;
    String sid, susername, spro_title, sprice, spay_txt, simg, sqty, sfo_price;
    ArrayAdapter<String> arrayAdapter;
    Context context;
    private SQLiteDatabase db;
    ArrayList<Setget_men> setget_homepage;
    FragmentTransaction transaction;
    FragmentManager manager;
    Integer count = 1;
    String s1, scart_count;
    SharedPreferences sp;
    int postion;
    TextView number;
    private int itemCount = 0;
    String[] size;
    String[] pincode;
    String a, b, c, d;
    TextView cart_number;

    public Product_desc() {
        // Required empty public constructor
    }

    public Product_desc(Context context, ArrayList<Setget_men> list, String s1, int position) {
        this.context = context;
        this.setget_homepage = list;
        this.s1 = s1;
        this.postion = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_desc, container, false);
        sp = getContext().getSharedPreferences(Constant.PER, MODE_PRIVATE);


        String s3 = setget_homepage.get(postion).getSubproduct_pincode();
        pincode = s3.split(",");
        for (String w : pincode) {
            Toast.makeText(context, w, Toast.LENGTH_SHORT).show();
        }
        String s4 = setget_homepage.get(postion).getSize_id();
        size = s4.split(",");
        for (String w : size) {
            Toast.makeText(context, w, Toast.LENGTH_SHORT).show();
        }
        pro_title = (TextView) view.findViewById(R.id.desc_title);
        price = (TextView) view.findViewById(R.id.price);
        desc_txt = (TextView) view.findViewById(R.id.desc_txt);
        fo_price = (TextView) view.findViewById(R.id.fo_price);
        plus = (ImageView) view.findViewById(R.id.plus);
        minus = (ImageView) view.findViewById(R.id.minus);
        qty = (TextView) view.findViewById(R.id.qty);
        qty_txt = (TextView) view.findViewById(R.id.qty_txt);
        clothsize = (Spinner) view.findViewById(R.id.desc_sp);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, size);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        clothsize.setAdapter(spinnerArrayAdapter);

        qty_txt.setText("1");
       /* Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        number = (TextView) toolbar.findViewById(R.id.item_count);*/
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1 = (Integer.parseInt(qty_txt.getText().toString()));
                if (s1 <= 2) {
                    minus.setEnabled(true);
                }
                count = count + 1;
                display(count);
                double e1 = Double.parseDouble(price.getText().toString());
                double e2 = Double.parseDouble(qty_txt.getText().toString());
                double tmmad = (e1 * e2);
                fo_price.setText(Double.toString(tmmad));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1 = (Integer.parseInt(qty_txt.getText().toString()));
                if (s1 == 1) {
                    minus.setEnabled(false);
                } else {
                    count = count - 1;
                    display(count);
                }


                double e1 = Double.parseDouble(price.getText().toString());
                double e2 = Double.parseDouble(qty_txt.getText().toString());
                double tmmad = (e1 * e2);
                fo_price.setText(Double.toString(tmmad));
            }
        });

        img1 = (ImageView) view.findViewById(R.id.img1);
        cart_btn = (Button) view.findViewById(R.id.cart_btn_all);

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getString(Constant.USER_ID, "").equalsIgnoreCase("")) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } else {
                    if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                        a = setget_homepage.get(postion).getSubproduct_id();
                        b = sp.getString(Constant.USER_ID, null);
                        c = qty_txt.getText().toString();
                        d = clothsize.getSelectedItem().toString();
                        new addCart().execute();
                    //    new getcartcount().execute();
                    } else {
                        new ConnectionDetector(getActivity()).connectiondetect();
                    }
                }

            }
        });
        Picasso.with(context).load(setget_homepage.get(postion).getSubproduct_image()).placeholder(R.drawable.k).into(img1);
        price.setText(setget_homepage.get(postion).getSubproduct_price());
        pro_title.setText(setget_homepage.get(postion).getSubproduct_name());
        desc_txt.setText(setget_homepage.get(postion).getSubproduct_desc());
        fo_price.setText(setget_homepage.get(postion).getSubproduct_price());
        qty.setText(setget_homepage.get(postion).getSubproduct_qty());
        return view;
    }

    private void getdata() {
        spro_title = pro_title.getText().toString();
        sprice = fo_price.getText().toString();
        sqty = qty_txt.getText().toString();
        simg = setget_homepage.get(postion).getSubproduct_image();
        susername = sp.getString(Constant.USERNAME, null);
        sfo_price = fo_price.getText().toString();
    }


    private void display(int number) {
        TextView displayInteger = (TextView) getActivity().findViewById(R.id.qty_txt);
        displayInteger.setText("" + number);

    }

    public class addCart extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("loading..");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();
            data.put("subproduct_master_id", a);
            data.put("user_master_id", b);
            data.put("quantity", c);
            data.put("size_master_code", d);
            return new MakeServiceCall().getPostData(Constant.SAMANTA_IP + "addProductToCart.php?action=addProductToCart", data);
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
                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Cart.class));
                } else {
                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
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
            return new MakeServiceCall().MakeServiceCall(Constant.SAMANTA_IP + "getCartDetails.php?action=getCartDetails&user_master_id=" + b, MakeServiceCall.GET);
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
