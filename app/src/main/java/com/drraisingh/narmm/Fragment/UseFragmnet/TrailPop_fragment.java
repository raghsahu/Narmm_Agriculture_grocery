package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.drraisingh.narmm.Adapter.CropTrailAdapter;
import com.drraisingh.narmm.Adapter.OrderHistoryAdapter;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Model.UseableModel.CropTrailModel;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.LoginActivity;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.Session_management;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.drraisingh.narmm.Config.BaseURL.GET_crop_trials;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class TrailPop_fragment extends Fragment {

    RecyclerView recycler_crop;

    ArrayList<CropTrailModel>cropTrailModels=new ArrayList<>();

    public TrailPop_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop_trial, container, false);
        ((MainActivity)  getActivity()).setTitle("Crop Trial");

        recycler_crop=view.findViewById(R.id.recycler_crop);

        // check internet connection
        if (ConnectivityReceiver.isConnected()) {
            makeGetInfoRequest(GET_crop_trials);
        } else {
            ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
        }




        return view;
    }

    private void makeGetInfoRequest(String get_crop_trials) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        String tag_json_obj = "json_login_req";

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("mobile", email);
//        params.put("pin", password);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.GET,
                get_crop_trials, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("crop_trial", response.toString());
                progressDialog.dismiss();
                try {
                    cropTrailModels.clear();
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                       // Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i=0; i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String crop_name = object.getString("crop_name");
                            String pdf = object.getString("pdf");

                        cropTrailModels.add(i,new CropTrailModel(id,crop_name,pdf));
                        }


                    } else {
                       // String error = response.getString("error");
                        Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
                    }


                    CropTrailAdapter categoryProduct_adapter = new CropTrailAdapter(cropTrailModels,getActivity());
                    recycler_crop.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recycler_crop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycler_crop.setAdapter(categoryProduct_adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }


}
