package com.example.user.app23;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.app23.Constant.ConnectionDetector;
import com.example.user.app23.Constant.Constant;
import com.example.user.app23.Constant.MakeServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class WomenFragment extends Fragment {
    ArrayList<Setget_men> setget_men;
    RecyclerView women_rv;
    Context context;
    Adapt_men adapt_men;
    FragmentManager manager;
    String s1, s2;
    SharedPreferences sp;
    int postion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_women, container, false);
        women_rv = (RecyclerView) view.findViewById(R.id.women_rv);
        sp = getActivity().getSharedPreferences(Constant.PER, MODE_PRIVATE);

        women_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        women_rv.setItemAnimator(new DefaultItemAnimator());
        setget_men = new ArrayList<>();
        manager = getFragmentManager();
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            new womendata().execute();
        } else {
            new ConnectionDetector(getActivity()).connectiondetect();
        }
        return view;
    }

    private class womendata extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return new MakeServiceCall().MakeServiceCall(Constant.SAMANTA_IP + "getProductByPersonMaster.php?action=getProductByPersonMaster&person_master_id=2", MakeServiceCall.GET);
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
                    JSONArray jsonArray = object.getJSONArray("response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Setget_men list = new Setget_men();
                        list.setSubproduct_id(jsonObject.getString("subproduct_master_id"));
                        list.setProduct_id(jsonObject.getString("product_master_id"));
                        list.setSize_id(jsonObject.getString("size_master_code"));
                        list.setPerson_id(jsonObject.getString("person_master_id"));
                        list.setSubproduct_name(jsonObject.getString("subproduct_master_name"));
                        list.setSubproduct_image(jsonObject.getString("subproduct_master_image"));
                        list.setSubproduct_price(jsonObject.getString("subproduct_master_price"));
                        list.setSubproduct_desc(jsonObject.getString("subproduct_master_description"));
                        list.setSubproduct_qty(jsonObject.getString("subproduct_master_quantity"));
                        list.setSubproduct_cod(jsonObject.getString("subproduct_cod_option"));
                        list.setSubproduct_pincode(jsonObject.getString("subproduct_available_pincode"));
                        setget_men.add(list);
                    }
                } else {
                    Toast.makeText(getActivity(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                }
                adapt_men = new Adapt_men(getActivity(), setget_men, manager);
                women_rv.setAdapter(adapt_men);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}