package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.drraisingh.narmm.Adapter.My_order_adapter;
import com.drraisingh.narmm.Adapter.OrderHistoryAdapter;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Model.My_order_model;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.Model.OrderHistoryModel.OrderHistory_model;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonArrayRequest;
import com.drraisingh.narmm.util.Session_management;
import com.rxandroidnetworking.RxAndroidNetworking;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.GET_ORDER_URL;


public class My_order_fragment extends Fragment {

    private static String TAG = My_order_fragment.class.getSimpleName();

    private RecyclerView rv_myorder;
    private ActionBar toolbar;
    private List<My_order_model> my_order_modelList = new ArrayList<>();

    public My_order_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.order));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);



        rv_myorder = (RecyclerView) view.findViewById(R.id.rv_myorder);
        rv_myorder.setLayoutManager(new LinearLayoutManager(getActivity()));

        Session_management sessionManagement = new Session_management(getActivity());
        String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        // check internet connection
        if (ConnectivityReceiver.isConnected()) {
           // makeGetOrderRequest(user_id);
            MyOrderHistory(user_id);//rxjava using call api
        } else {
            ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
        }


        return view;
    }

    private void MyOrderHistory(String user_id) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_ORDER_URL)
                .addBodyParameter("farmer_id", user_id)
                //.addBodyParameter("company_id", comp_id)
                .build()
                .getObjectObservable(OrderHistory_model.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderHistory_model>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("reg_rescomp","complete");
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error",e.toString());
                        // handle error
                    }
                    @Override
                    public void onNext(OrderHistory_model user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {

                            Log.e("reg_responce123",""+user.getResponce());
                            if (user.getResponce()==true){

                                OrderHistoryAdapter categoryProduct_adapter = new OrderHistoryAdapter(user.getData());
                                rv_myorder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                rv_myorder.setAdapter(categoryProduct_adapter);
                                // categoryProduct_adapter.notifyDataSetChanged();


                            }else {
                                // Toast.makeText(context, "No product available", Toast.LENGTH_SHORT).show();
                                Log.e("reg_respo",""+user.getResponce());
                            }

                        }catch (Exception e){
                            Log.e("reg_rescatch",e.getMessage());
                        }

                    }
                });

    }

    /**
     * Method to make json array request where json response starts wtih
     */
    private void makeGetOrderRequest(String userid) {

        // Tag used to cancel the request
        String tag_json_obj = "json_socity_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("farmer_id", userid);

        CustomVolleyJsonArrayRequest jsonObjReq = new CustomVolleyJsonArrayRequest(Request.Method.POST,
                GET_ORDER_URL, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                Gson gson = new Gson();
                Type listType = new TypeToken<List<My_order_model>>() {
                }.getType();

                my_order_modelList = gson.fromJson(response.toString(), listType);

                My_order_adapter adapter = new My_order_adapter(my_order_modelList);
                rv_myorder.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if(my_order_modelList.isEmpty()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

}
