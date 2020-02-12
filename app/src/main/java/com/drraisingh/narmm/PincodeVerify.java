package com.drraisingh.narmm;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;

public class PincodeVerify extends AppCompatActivity {
EditText input_mobile;

    private static String TAG = PincodeVerify.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        input_mobile=(EditText)findViewById(R.id.input_mobile);




    }

    public  void send(View view){
        if(input_mobile.getText().toString().isEmpty()){
            input_mobile.setError("Fill the pin code");

        }else if(isPasswordValid(input_mobile.getText().toString())){
            input_mobile.setError("Picode Must Be 6 Digit");

        }else {
            checkConnection();

        }





    }


    private void makeLoginRequest(String email) {

        // Tag used to cancel the request
        String tag_json_obj = "json_login_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("pincode", email);


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.PINCODE_Check, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Log.d("data", response.toString());
                try {



                        JSONObject obj = new JSONObject(String.valueOf(response));
                    String res = obj.getString("responce");
                        String msg = obj.getString("msg");



                        if(res.equals("true")){
                            Toast.makeText(PincodeVerify.this,"આ સુવિધા આપના શહેર માં ઉપલબ્ધ છે.",Toast.LENGTH_LONG).show();



                        }else {
                            Toast.makeText(PincodeVerify.this,"ટુંક સમય માં આપના શહેર આ સુવિધા ચાલુ થઈ જશે.",Toast.LENGTH_LONG).show();

                        }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(PincodeVerify.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        makeLoginRequest(input_mobile.getText().toString());
    }


    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (!isConnected) {
            message = ""+getResources().getString(R.string.no_internet);
            color = Color.RED;

            Toast.makeText(PincodeVerify.this,R.string.no_internet,Toast.LENGTH_LONG).show();

                /*.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })*/;


        }
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;
            default:
                return false;
        }


    }




}
