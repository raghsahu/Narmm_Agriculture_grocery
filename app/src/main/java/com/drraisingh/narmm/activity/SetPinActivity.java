package com.drraisingh.narmm.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;

public class SetPinActivity extends AppCompatActivity {
    EditText one,two,three,four,five,six;
    String data,mobile;

    private static String TAG = SetPinActivity.class.getSimpleName();
    private String formar_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__otp);
        one=(EditText)findViewById(R.id.one);
        two=(EditText)findViewById(R.id.two);
        three=(EditText)findViewById(R.id.three);
        four=(EditText)findViewById(R.id.four);
        five=(EditText)findViewById(R.id.five);
        six=(EditText)findViewById(R.id.six);


        try {
            Intent intent=getIntent();
           // mobile=intent.getStringExtra("mobile");
            formar_id=intent.getStringExtra("formar_id");
        }catch (Exception e){

        }


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
    }


    public  void verifyotp(View view){

        data=one.getText().toString()+
                two.getText().toString()+
                three.getText().toString() +
                four.getText().toString()+five.getText().toString()+six.getText().toString();

           checkConnection();

    }


    private void makeLoginRequest(String data,String formar_id) {
        final ProgressDialog pDialog = new ProgressDialog(SetPinActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        // Tag used to cancel the request
        String tag_json_obj = "json_login_req";

        Map<String, String> params = new HashMap<String, String>();
        params.put("farmer_id", formar_id);
        params.put("pin", data);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.farmer_pin_set, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
               // Log.d(TAG, response.toString());
                Log.e("data","SET PIN ACTIVITY --->>>   "+ response.toString());
                pDialog.dismiss();
                try {

                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String res = obj.getString("responce");

                    JSONObject ob1 =obj.getJSONObject("data");
                    String mail = ob1.getString("email");
                    String mobile = ob1.getString("mobile_no");
                    String full_name = ob1.getString("full_name");
                    String farmer_id = ob1.getString("farmer_id");

                    String total_acres = ob1.getString("total_acres");
                    String crop_first = ob1.getString("crop_first");
                    String crop_second = ob1.getString("crop_second");
                    String crop_third = ob1.getString("crop_third");
                    String crop_other = ob1.getString("crop_other");
                    String crop_first_qty = ob1.getString("crop_first_qty");
                    String crop_second_qty = ob1.getString("crop_second_qty");
                    String crop_third_qty = ob1.getString("crop_third_qty");
                    String crop_other_qty = ob1.getString("crop_other_qty");

                    if(res.equals("true")){
                        Toast.makeText(SetPinActivity.this,""+res,Toast.LENGTH_LONG).show();

                        Intent i = new Intent(SetPinActivity.this, UpdateAcarActivity.class);

                        i.putExtra("Amount",getIntent().getStringExtra("Amount"));
                        i.putExtra("GstAmt",getIntent().getStringExtra("GstAmt"));
                        i.putExtra("NetAmt",getIntent().getStringExtra("NetAmt"));
                        i.putExtra("mobile",mobile);
                        i.putExtra("mail",mail);
                        i.putExtra("name",full_name);

                        i.putExtra("farmar_id",farmer_id);
                        i.putExtra("total_acres",total_acres);
                        i.putExtra("crop_first",crop_first);
                        i.putExtra("crop_second",crop_second);
                        i.putExtra("crop_third",crop_third);
                        i.putExtra("crop_other",crop_other);
                        i.putExtra("crop_first_qty",crop_first_qty);
                        i.putExtra("crop_second_qty",crop_second_qty);
                        i.putExtra("crop_third_qty",crop_third_qty);
                        i.putExtra("crop_other_qty",crop_other_qty);


                        startActivity(i);
                        finish();

                    }else {
                        Toast.makeText(SetPinActivity.this,""+res,Toast.LENGTH_LONG).show();
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
                    Toast.makeText(SetPinActivity.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        makeLoginRequest(data,formar_id);
    }


    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (!isConnected) {
            message = ""+getResources().getString(R.string.no_internet);
            color = Color.RED;

            Toast.makeText(SetPinActivity.this,R.string.no_internet,Toast.LENGTH_LONG).show();

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






}
