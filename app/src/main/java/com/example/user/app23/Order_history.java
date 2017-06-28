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

import com.example.user.app23.Constant.ConnectionDetector;
import com.example.user.app23.Constant.Constant;
import com.example.user.app23.Constant.MakeServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Order_history extends Fragment {
    ArrayList<Setget_order> setget_order;
    RecyclerView order_rv;
    Context context;
    Adapt_order adapt_order;
    FragmentManager manager;
    SharedPreferences sp;
    String user_id;

    public Order_history() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        sp = getActivity().getSharedPreferences(Constant.PER, getActivity().MODE_PRIVATE);
        order_rv = (RecyclerView) view.findViewById(R.id.order_rv);
        order_rv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        order_rv.setItemAnimator(new DefaultItemAnimator());
        manager = getFragmentManager();
        setget_order = new ArrayList<>();
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            user_id = sp.getString(Constant.USER_ID, null);
            new getorderdata().execute();
        } else {
            new ConnectionDetector(getActivity()).connectiondetect();
        }
        return view;
    }

    private class getorderdata extends AsyncTask<String, String, String> {
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
            return new MakeServiceCall().MakeServiceCall(Constant.SAMANTA_IP + "getOrderDetails.php?action=getOrderDetails&user_master_id=" + user_id, MakeServiceCall.GET);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            try {
                JSONObject jsonObject = new JSONObject(s.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Setget_order list = new Setget_order();
                    list.setOrder_image(object.getString("subproduct_master_image"));
                    list.setOrder_name(object.getString("subproduct_master_name"));
                    list.setOrder_qty(object.getString("subproduct_quantity"));
                    list.setOrder_price(object.getString("subproduct_price"));
                    list.setOrder_status(object.getString("order_status_name"));
                    setget_order.add(list);
                }
                adapt_order = new Adapt_order(getActivity(), setget_order, manager);
                order_rv.setAdapter(adapt_order);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
