package com.example.user.app23;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.app23.Constant.ConnectionDetector;
import com.example.user.app23.Constant.Constant;
import com.example.user.app23.Constant.MakeServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class Account extends Fragment {
    private EditText fname, lname, email;
    TextView edit;
    private Button update;
    private String sfname, slname, semail, user_id;
    SharedPreferences sp;

    public Account() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        sp = getActivity().getSharedPreferences(Constant.PER, MODE_PRIVATE);
        fname = (EditText) view.findViewById(R.id.account_fname);
        lname = (EditText) view.findViewById(R.id.account_lname);
        email = (EditText) view.findViewById(R.id.account_email);
        update = (Button) view.findViewById(R.id.account_btn_update);
        edit = (TextView) view.findViewById(R.id.edit_account);
        update.setVisibility(View.GONE);
        fname.setEnabled(false);
        lname.setEnabled(false);
        email.setEnabled(false);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.VISIBLE);
                fname.setEnabled(true);
                lname.setEnabled(true);
                email.setEnabled(false);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    user_id = sp.getString(Constant.USER_ID, null);
                    sfname = fname.getText().toString();
                    slname = lname.getText().toString();
                    new updateaccount().execute();
                } else {
                    new ConnectionDetector(getActivity()).connectiondetect();
                }
            }
        });
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            user_id = sp.getString(Constant.USER_ID, null);
            new getprofile().execute();
        } else {
            new ConnectionDetector(getActivity()).connectiondetect();
        }
        return view;
    }


    private class getprofile extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_master_id", user_id);
            return new MakeServiceCall().getPostData(Constant.SAMANTA_IP + "getUserInformationById.php?action=getUserInformationById", hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            try {
                JSONObject jsonObject = new JSONObject(s.toString());
                if (jsonObject.getString("Status").equalsIgnoreCase("1")) {
                    JSONArray array = jsonObject.getJSONArray("response");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        fname.setText(object.getString("user_master_name"));
                        lname.setText(object.getString("user_master_contact"));
                        email.setText(object.getString("user_master_email"));
                    }
                } else {
                    Toast.makeText(getActivity(), jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class updateaccount extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_master_id", user_id);
            hashMap.put("user_master_name", sfname);
            hashMap.put("user_master_contact", slname);
            return new MakeServiceCall().getPostData(Constant.SAMANTA_IP + "updateUserProfile.php?action=updateUserProfile", hashMap);
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
                    fname.setEnabled(false);
                    lname.setEnabled(false);
                    email.setEnabled(false);
                    update.setVisibility(View.GONE);
                    startActivity(new Intent(getActivity(), Reside.class));
                } else {
                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

