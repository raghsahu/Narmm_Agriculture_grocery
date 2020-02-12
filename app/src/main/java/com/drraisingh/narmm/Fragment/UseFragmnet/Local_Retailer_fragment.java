package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
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
import com.drraisingh.narmm.Adapter.Delivery_get_address_adapter;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Model.UseableModel.Shop_address_model;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.GPSTracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.drraisingh.narmm.Config.BaseURL.GET_current_location_shop_list;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class Local_Retailer_fragment extends Fragment {

    RecyclerView recycler_retailer;
    private List<Shop_address_model> delivery_address_modelList = new ArrayList<>();
    private Delivery_get_address_adapter adapter;
    GPSTracker tracker;
    double longitude, latitude;
    String address;
    private String city;

    public Local_Retailer_fragment() {
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
        View view = inflater.inflate(R.layout.fragment_local_retailer, container, false);
        ((MainActivity)  getActivity()).setTitle("Local retailer");

        recycler_retailer =view.findViewById(R.id.recycler_retailer);

        try {
            tracker = new GPSTracker(getActivity());
            if (tracker.canGetLocation()) {
                latitude = tracker.getLatitude();
                longitude = tracker.getLongitude();
                Log.e("current_lat ", " " + latitude);
                Log.e("current_Lon ", " " + longitude);
                address = getAddress(latitude, longitude);
                Log.e("Address ", " " + getAddress(latitude, longitude));
            } else {
                tracker.showSettingsAlert();
            }
        } catch (Exception e) {

        }


        // check internet connection
        if (ConnectivityReceiver.isConnected()) {
            makeGetInfoRequest(GET_current_location_shop_list,city);
        } else {
            ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
        }



        return view;
    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

       city = addresses.get(0).getLocality();
//        state = addresses.get(0).getAdminArea();
//        country = addresses.get(0).getCountryName();
//        postalCode = addresses.get(0).getPostalCode();
//        knownName = addresses.get(0).getFeatureName();

//        Log.e("fullAddress", "" + address);
       Log.e("city", "" + city);
//        Log.e("state", "" + state);
//        Log.e("country", "" + country);
//        Log.e("postalCode", "" + postalCode);
//        Log.e("knownName", "" + knownName);
// Only if available else return NULL
       // return address;
        return city;
    }

    private void makeGetInfoRequest(String get_current_location_shop_list, String city) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_get_address_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("city_name", city);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                get_current_location_shop_list, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("local_retail", response.toString());
                progressDialog.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {

                        delivery_address_modelList.clear();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Shop_address_model>>() {
                        }.getType();

                        delivery_address_modelList = gson.fromJson(response.getString("data"), listType);

                       // Log.e("responce_addres",delivery_address_modelList.get(0).getShop_name());
                        adapter = new Delivery_get_address_adapter(delivery_address_modelList,"Local");
                        recycler_retailer.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recycler_retailer.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        if (delivery_address_modelList.isEmpty()) {
                            if (getActivity() != null) {
                                //Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                VolleyLog.d("Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

}
