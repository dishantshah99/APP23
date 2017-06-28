package com.example.user.app23;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.app23.Constant.Constant;
import com.example.user.app23.Constant.MakeServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Address_user extends AppCompatActivity {
    EditText ship_address, ship_code, bill_address, bill_code;
    Button save;
    SharedPreferences sp;
    String sship_address, sship_code, sbill_address, sbill_code, user_id;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_address_user);
        sp = getSharedPreferences(Constant.PER, MODE_PRIVATE);
        user_id = sp.getString(Constant.USER_ID, null);
        setdata();
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ship_address.getText().toString().trim().length() == 0) {
                    ship_address.setError("Enter Your First name");
                } else if (ship_code.getText().toString().trim().length() == 0) {
                    ship_code.setError("Enter Your Last name");
                } else if (bill_address.getText().toString().trim().length() == 0) {
                    bill_address.setError("Enter Your Mobile number");
                } else if (bill_code.getText().toString().trim().length() == 0) {
                    bill_code.setError("Enter Your Address");
                } else {
                    getdata();
                    new setaddress().execute();
                }
            }
        });
    }

    private void getdata() {
        sship_address = ship_address.getText().toString();
        sship_code = ship_code.getText().toString();
        sbill_address = bill_address.getText().toString();
        sbill_code = bill_code.getText().toString();
    }

    private void setdata() {
        ship_address = (EditText) findViewById(R.id.ship_address);
        ship_code = (EditText) findViewById(R.id.ship_code);
        bill_address = (EditText) findViewById(R.id.bill_address);
        bill_code = (EditText) findViewById(R.id.bill_code);
        save = (Button) findViewById(R.id.btn_save);
    }


    private class setaddress extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setMessage("loading..");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> data = new HashMap<>();
            data.put("user_master_id", user_id);
            data.put("user_billing_address", sbill_address);
            data.put("user_shipping_address", sship_address);
            data.put("user_shipping_pincode", sship_code);
            data.put("user_billing_pincode", sbill_code);
            return new MakeServiceCall().getPostData(Constant.SAMANTA_IP + "submitOrderInformation.php?action=submitOrderInformation", data);
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
                    startActivity(new Intent(context, Reside.class));
                } else {
                    Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
