package com.drraisingh.narmm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.drraisingh.narmm.util.Session_management;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = LoginActivity.class.getSimpleName();

    private Button btn_continue, btn_register;
    LinearLayout ll_mobile;
    private EditText one,two,three,four,five,six, et_login_mobile;
    private TextView tv_password, tv_email,btn_forgot;
    ProgressDialog pd;
    Session_management sessionManagement;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title

        sessionManagement = new Session_management(LoginActivity.this);
        setContentView(R.layout.activity_login);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        one = (EditText) findViewById(R.id.one);
        two= (EditText) findViewById(R.id.two);
        three = (EditText) findViewById(R.id.three);
        four = (EditText) findViewById(R.id.four);
        five = (EditText) findViewById(R.id.five);
        six= (EditText) findViewById(R.id.six);
        et_login_mobile = (EditText) findViewById(R.id.et_login_mobile);
        tv_password = (TextView) findViewById(R.id.tv_login_password);
        tv_email = (TextView) findViewById(R.id.tv_login_email);
        btn_continue = (Button) findViewById(R.id.btnContinue);
        btn_register = (Button) findViewById(R.id.btnRegister);
        btn_forgot = (TextView) findViewById(R.id.btnForgot);
        ll_mobile = findViewById(R.id.ll_mobile);

        btn_continue.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forgot.setOnClickListener(this);

        one.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (one.getText().toString().length() == 1)     //size as per your requirement
                {
                    two.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        two.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (two.getText().toString().length() == 1)     //size as per your requirement
                {
                    three.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        three.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (three.getText().toString().length() == 1)     //size as per your requirement
                {
                    four.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        four.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (four.getText().toString().length() == 1)     //size as per your requirement
                {
                    five.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        five.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (five.getText().toString().length() == 1)     //size as per your requirement
                {
                    six.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        six.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    five.requestFocus();
                }
                return false;
            }
        });

        five.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    four.requestFocus();
                }
                return false;
            }
        });
        four.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    three.requestFocus();
                }
                return false;
            }
        });
        three.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    two.requestFocus();
                }
                return false;
            }
        });
        two.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    one.requestFocus();
                }
                return false;
            }
        });



        if(sessionManagement.isLoggedIn()) {
               ll_mobile.setVisibility(View.GONE);
               
        }else{
               ll_mobile.setVisibility(View.VISIBLE);

           }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnContinue) {
            attemptLogin();
        } else if (id == R.id.btnRegister) {
            Intent startRegister = new Intent(LoginActivity.this, Registrationpage.class);
            startActivity(startRegister);
        }else if (id == R.id.btnForgot) {
            Intent startRegister = new Intent(LoginActivity.this, ForgotActivity.class);
            startActivity(startRegister);
        }
    }

    private void attemptLogin() {

        tv_email.setText("Mobile Number (Required)");
        tv_password.setText("Pin (Required)");

        tv_password.setTextColor(getResources().getColor(R.color.dark_gray));
        tv_email.setTextColor(getResources().getColor(R.color.dark_gray));

        String getpassword1 = one.getText().toString();
        String getpassword2 = two.getText().toString();
        String getpassword3 = three.getText().toString();
        String getpassword4 = four.getText().toString();
        String getpassword5 = five.getText().toString();
        String getpassword6 = six.getText().toString();
        String getpassword = getpassword1+getpassword2+getpassword3+getpassword4+getpassword5+getpassword6;
        boolean cancel = false;
        View focusView = null;



        String getmobile;
        if(sessionManagement.isLoggedIn()) {
            getmobile = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
            
        }else{
            getmobile = et_login_mobile.getText().toString();
            if (TextUtils.isEmpty(getmobile)) {
                tv_email.setTextColor(getResources().getColor(R.color.color_3));
                focusView = et_login_mobile;
                cancel = true;
            } else if (!isPhoneValid(getmobile)) {
                tv_email.setText("Invalid Mobile Number");
                tv_email.setTextColor(getResources().getColor(R.color.color_3));
                focusView = et_login_mobile;
                cancel = true;
            }
            
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
                makeLoginRequest(getmobile,getpassword);
            }
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length()>5;
    }
    private boolean isPhoneValid(String phoneno) {
        //TODO: Replace this with your own logic
        return phoneno.length() > 9;
    }
    /**
     * Method to make json object request where json response starts wtih
     */
    private void makeLoginRequest(String email, final String password) {

        // Tag used to cancel the request

         pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("loading");
        pd.show();


        String tag_json_obj = "json_login_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", email);
        params.put("pin", password);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.LOGIN_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                 pd.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                    if (status) {
                        JSONObject obj = response.getJSONObject("data");
                        String user_id = obj.getString("farmer_id");
                        String user_fullname = obj.getString("full_name");
                        String user_email = obj.getString("email");
                        String user_phone = obj.getString("mobile_no");
                        String user_image = obj.getString("profile_image");
                        String payment_status = obj.getString("payment_status");
                       // Toast.makeText(LoginActivity.this, ""+payment_status, Toast.LENGTH_SHORT).show();

                        if (payment_status.equalsIgnoreCase("0")){

                            String mail = obj.getString("email");
                            String mobile = obj.getString("mobile_no");
                            String full_name = obj.getString("full_name");
                            String farmer_id = obj.getString("farmer_id");

                            String total_acres = obj.getString("total_acres");
                            String crop_first = obj.getString("crop_first");
                            String crop_second = obj.getString("crop_second");
                            String crop_third = obj.getString("crop_third");
                            String crop_other = obj.getString("crop_other");
                            String crop_first_qty = obj.getString("crop_first_qty");
                            String crop_second_qty = obj.getString("crop_second_qty");
                            String crop_third_qty = obj.getString("crop_third_qty");
                            String crop_other_qty = obj.getString("crop_other_qty");

                           // Toast.makeText(LoginActivity.this, "Please pay registration charge", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, UpdateAcarActivity.class);
                            i.putExtra("total_acres",total_acres);
                            i.putExtra("crop_first",crop_first);
                            i.putExtra("crop_second",crop_second);
                            i.putExtra("crop_third",crop_third);
                            i.putExtra("crop_other",crop_other);
                            i.putExtra("crop_first_qty",crop_first_qty);
                            i.putExtra("crop_second_qty",crop_second_qty);
                            i.putExtra("crop_third_qty",crop_third_qty);
                            i.putExtra("crop_other_qty",crop_other_qty);
                            i.putExtra("farmar_id",farmer_id);
                            startActivity(i);
                        }else {
                            Session_management sessionManagement = new Session_management(LoginActivity.this);
                            sessionManagement.createLoginSession(user_id,user_email,user_fullname,user_phone,
                                    user_image,"","","","",password);
                            sessionManagement.setLogin(true);
                            Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }


                    } else {
                        String error = response.getString("error");
                        Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }



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
