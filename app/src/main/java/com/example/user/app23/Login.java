package com.example.user.app23;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_MASTER_ID = "id";
    String user_id;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String susername, spassword;
    SharedPreferences sp;
    ArrayList<HashMap<String, String>> list;
    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener1 mListener;

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_login, container, false);
        sp = getActivity().getSharedPreferences(Constant.PER, getActivity().MODE_PRIVATE);
        Button signup = (Button) mView.findViewById(R.id.btn_login);
        final TextView username = (TextView) mView.findViewById(R.id.username);
        final TextView password = (TextView) mView.findViewById(R.id.password);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().length() == 0) {
                    username.setError("Enter Username");
                } else if (password.getText().toString().trim().length() == 0) {
                    password.setError("Enter Password");
                } else if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    susername = username.getText().toString();
                    spassword = password.getText().toString();
                    new login().execute();

                } else {
                    new ConnectionDetector(getActivity()).connectiondetect();
                }
            }
        });
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction1(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener1 {
        // TODO: Update argument type and name
        void onFragmentInteraction1(Uri uri);
    }

    private class login extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading..");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap data = new HashMap<>();
            data.put("user_master_email", susername);
            data.put("user_master_password", spassword);
            return new MakeServiceCall().getPostData(Constant.SAMANTA_IP+"getLogin.php?action=getLogin", data);
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
                    JSONArray array = object.getJSONArray("response");
                    list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        HashMap<String, String> data = new HashMap<String, String>();
                        data.put(USER_MASTER_ID, jsonObject.getString("user_master_id"));
                        user_id = data.get(USER_MASTER_ID).toString();
                        sp.edit().putString(Constant.USER_ID, user_id).commit();
                        startActivity(new Intent(getActivity(), Reside.class));
                        Toast.makeText(getActivity(), user_id, Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
