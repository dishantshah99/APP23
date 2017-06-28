package com.example.user.app23;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.app23.Constant.ConnectionDetector;
import com.example.user.app23.Constant.Constant;
import com.example.user.app23.Constant.MakeServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Signup extends Fragment {
    private Signup.OnFragmentInteractionListener2 mListener;
    ViewPager mViewPager;
    EditText name, mobile_no, email, password, co_password;
    String sname, smobile_no, semail, spassword;

    public Signup() {
    }

    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_signup, container, false);

        Button signup = (Button) mView.findViewById(R.id.btn_signup);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewPager1);
        name = (EditText) mView.findViewById(R.id.signup_name);
        mobile_no = (EditText) mView.findViewById(R.id.mobile_no);
        email = (EditText) mView.findViewById(R.id.email);
        password = (EditText) mView.findViewById(R.id.password);
        co_password = (EditText) mView.findViewById(R.id.co_password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Your Name", Toast.LENGTH_SHORT).show();
                } else if (mobile_no.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Your Email ID", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Your Password", Toast.LENGTH_SHORT).show();
                } else if (co_password.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Re Enter Your Password", Toast.LENGTH_SHORT).show();
                } else {
                    String pass, repass;
                    pass = password.getText().toString();
                    repass = co_password.getText().toString();
                    if (pass.equals(repass)) {
                        new ConnectionDetector(getActivity()).isConnectingToInternet();
                        new signup().execute();
                        getdata();
                    } else {
                        new ConnectionDetector(getActivity()).connectiondetect();
                        Toast.makeText(getActivity(), "new password and confirm password does not match..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return mView;
    }

    private void getdata() {
        sname = name.getText().toString();
        semail = email.getText().toString();
        smobile_no = mobile_no.getText().toString();
        spassword = password.getText().toString();
    }

    private class signup extends AsyncTask<String, String, String> {
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
            data.put("user_master_name", sname);
            data.put("user_master_contact", smobile_no);
            data.put("user_master_email", semail);
            data.put("user_master_password", spassword);
            return new MakeServiceCall().getPostData(Constant.SAMANTA_IP+"submitUserInformation.php?action=submitUserInformation", data);
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
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } else {
                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnFragmentInteractionListener2 {
        // TODO: Update argument type and name
        void onFragmentInteraction1(Uri uri);
    }
}
