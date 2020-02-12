package com.drraisingh.narmm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

import org.json.JSONObject;

import java.net.URLEncoder;

import com.drraisingh.narmm.activity.SetPinActivity;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.Utilities;

public class RegisterActivity extends AppCompatActivity {

    private static String TAG = RegisterActivity.class.getSimpleName();

    private EditText et_phone, et_name, one,two,three,four,five,six, et_email;
    private Button btn_register;
    private TextView tv_phone, tv_name, tv_password, tv_email;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title

        setContentView(R.layout.activity_register);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        et_phone = (EditText) findViewById(R.id.et_reg_phone);
        et_name = (EditText) findViewById(R.id.et_reg_name);
        one = (EditText) findViewById(R.id.one);
        two= (EditText) findViewById(R.id.two);
        three= (EditText) findViewById(R.id.three);
       four = (EditText) findViewById(R.id.four);
        five = (EditText) findViewById(R.id.five);
        six = (EditText) findViewById(R.id.six);
        et_email = (EditText) findViewById(R.id.et_reg_email);
        tv_password = (TextView) findViewById(R.id.tv_reg_password);
        tv_phone = (TextView) findViewById(R.id.tv_reg_phone);
        tv_name = (TextView) findViewById(R.id.tv_reg_name);
        tv_email = (TextView) findViewById(R.id.tv_reg_email);
        btn_register = (Button) findViewById(R.id.btnRegister);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {

        tv_phone.setText(getResources().getString(R.string.et_login_phone_hint));
        tv_email.setText(getResources().getString(R.string.tv_login_email));
        tv_name.setText(getResources().getString(R.string.tv_reg_name_hint));
        tv_password.setText(getResources().getString(R.string.tv_login_password));

        tv_name.setTextColor(getResources().getColor(R.color.dark_gray));
        tv_phone.setTextColor(getResources().getColor(R.color.dark_gray));
        tv_password.setTextColor(getResources().getColor(R.color.dark_gray));
        tv_email.setTextColor(getResources().getColor(R.color.dark_gray));

        String getphone = et_phone.getText().toString();
        String getname = et_name.getText().toString();
        String getpassword = one+" "+two+" "+three+" "+four+" "+five+" "+six;
        String getemail = et_email.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(getname)) {
            tv_name.setTextColor(getResources().getColor(R.color.color_3));
            focusView = et_name;
            cancel = true;
            et_name.setFocusable(true);
        }
        if (TextUtils.isEmpty(getphone)) {
            tv_phone.setTextColor(getResources().getColor(R.color.color_3));

            cancel = true;
        } else if (!isPhoneValid(getphone)) {
            tv_phone.setText(getResources().getString(R.string.phone_too_short));
            tv_phone.setTextColor(getResources().getColor(R.color.color_3));


            cancel = true;
        }



        if (TextUtils.isEmpty(getpassword)) {
            tv_password.setTextColor(getResources().getColor(R.color.color_3));

            cancel = true;
        } else if (!isPasswordValid(getpassword)) {
            tv_password.setText(getResources().getString(R.string.password_too_short));
            tv_password.setTextColor(getResources().getColor(R.color.color_3));

            cancel = true;
        }

        if (TextUtils.isEmpty(getemail)) {
            tv_email.setTextColor(getResources().getColor(R.color.color_3));
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(getemail)) {
            tv_email.setText(getResources().getString(R.string.invalide_email_address));
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
                new Regisster(getname,getphone,getemail,getpassword).execute();

            }
        }


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid(String phoneno) {
        //TODO: Replace this with your own logic
        return phoneno.length() > 9;
    }

    private boolean isName(String name) {
        //TODO: Replace this with your own logic
        return name.length()>4;
    }

    /**
     * Method to make json object request where json response starts wtih
     */



    class Regisster extends AsyncTask<String,String,String> {
        String result=null;

        String name,phone,email1,password1;

        public Regisster(String getname, String getphone, String getemail, String getpassword) {
            this.name=getname;
            this.phone=getphone;
            this.email1=getemail;
            this.password1=getpassword;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDoalog = new ProgressDialog(RegisterActivity.this);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Its loading....");
            progressDoalog.setTitle("Please Wait...");

            // show it
            progressDoalog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                String urlParameters =
                        "user_fullname=" + URLEncoder.encode(name, "UTF-8")+
                                "&user_phone=" + URLEncoder.encode(phone, "UTF-8")+
                                "&user_email=" + URLEncoder.encode(email1, "UTF-8")+
                                "&password=" + URLEncoder.encode(password1, "UTF-8");
                result = Utilities.postParamsAndfindJSON("https://harshadbhaifruitcompany.com/hfcmall/index.php/api/signup?", urlParameters);
           Log.d("tag",result);
            }
            catch (Exception e){}
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (result == null) {
                progressDoalog.dismiss();

            } else {
                try {
                    progressDoalog.dismiss();
                    JSONObject json = new JSONObject(result);
                    Boolean status = json.getBoolean("responce");

                    if (status) {
                        //IDHAR NHI JARAHA HAI CHECK THIS

                        Intent i = new Intent(RegisterActivity.this, SetPinActivity.class);
                        i.putExtra("mobile",phone);
                        startActivity(i);
                        finish();


                    } else if (json.getString("responce").equalsIgnoreCase("false")) {
                        Toast.makeText(RegisterActivity.this,json.getString("error"),Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    /*Utility.ShowToastMessage(mContext, "please enter correct username and password");*/

                    e.printStackTrace();
                }
            }
        }
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
