package com.drraisingh.narmm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.drraisingh.narmm.activity.SetPinActivity;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;

public class MobileVerify extends AppCompatActivity {
    EditText input_mobile;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static String TAG = MobileVerify.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verify);
        input_mobile=(EditText)findViewById(R.id.input_mobile1);
         Intent mobile=getIntent();
         input_mobile.setText(mobile.getStringExtra("mobile"));

        input_mobile.setEnabled(false);

    }

    public  void verify(View view){
        if(input_mobile.getText().toString().isEmpty()){
            input_mobile.setError("Fill the pin code");

        }else if(isPasswordValid(input_mobile.getText().toString())){
            input_mobile.setError("Picode Must Be 10 Digit");

        }else {
            checkConnection();

        }

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
        final ProgressDialog pDialog = new ProgressDialog(MobileVerify.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        // Tag used to cancel the request
        String tag_json_obj = "json_login_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("mobileno", email);


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.MOBILE_OTP, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                pDialog.dismiss();
                Log.d("data", response.toString());
                try {



                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String res = obj.getString("responce");
                    String msg = obj.getString("msg");



                    if(res.equals("true")){
                        Intent i = new Intent(MobileVerify.this, SetPinActivity.class);
                        i.putExtra("mobile1",input_mobile.getText().toString());
                        startActivity(i);
                        finish();
                        SharedPreferences sharedPreferences=getSharedPreferences("mobile",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("res","yes");
                        editor.commit();
                        editor.clear();

                    }else {
                        Toast.makeText(MobileVerify.this,"Service Not Available At Your Society",Toast.LENGTH_LONG).show();

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
                    Toast.makeText(MobileVerify.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
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

            Toast.makeText(MobileVerify.this,R.string.no_internet,Toast.LENGTH_LONG).show();

                /*.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })*/;


        }
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 10;
    }





}
