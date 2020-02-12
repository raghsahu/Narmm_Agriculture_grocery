package com.drraisingh.narmm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = ForgotActivity.class.getSimpleName();

    private Button btn_continue;
    private EditText et_email;
    private TextView tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        et_email = (EditText) findViewById(R.id.et_login_email);
        tv_email = (TextView) findViewById(R.id.tv_login_email);
        btn_continue = (Button) findViewById(R.id.btnContinue);

        btn_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnContinue) {
            attemptForgot();
        }
    }

    private void attemptForgot() {

        tv_email.setText("Email Id (Required)");

        tv_email.setTextColor(getResources().getColor(R.color.dark_gray));

        String getemail = et_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(getemail)) {
            tv_email.setTextColor(getResources().getColor(R.color.color_3));
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(getemail)) {
            tv_email.setText("Email Id Number");
            tv_email.setTextColor(getResources().getColor(R.color.color_3));
            focusView = et_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null)
                focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (ConnectivityReceiver.isConnected()) {
                makeForgotRequest(getemail);
            }
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private boolean isPhoneValid(String phoneno) {
        //TODO: Replace this with your own logic
        return phoneno.length() > 9;
    }
    /**
     * Method to make json object request where json response starts wtih
     */
    private void makeForgotRequest(String email) {
        final ProgressDialog pDialog = new ProgressDialog(ForgotActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        // Tag used to cancel the request
        String tag_json_obj = "json_forgot_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.FORGOT_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                pDialog.dismiss();

                try {
                    Boolean status = response.getBoolean("responce");
                    String error = response.getString("error");
                    if (status) {

                        Toast.makeText(ForgotActivity.this, "" + error, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(ForgotActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(ForgotActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ForgotActivity.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
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
