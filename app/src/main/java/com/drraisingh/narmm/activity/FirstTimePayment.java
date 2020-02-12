package com.drraisingh.narmm.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.drraisingh.narmm.Config.BaseURL;
import com.drraisingh.narmm.Model.OrderModel.OrderPaymentModel;
import com.drraisingh.narmm.Model.RegistraionModel.FarmerRegistrationModel;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.util.Session_management;
import com.rxandroidnetworking.RxAndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.drraisingh.narmm.Config.BaseURL.GET_farmer_payment;
import static com.drraisingh.narmm.Config.BaseURL.payment;

public class FirstTimePayment extends AppCompatActivity {

    RadioButton radio_online;
    private Session_management sessionManagement;
    String getphone, getname;
    Button submit, updae_acar;
    TextView tv_number, tv_amt;
    String Amount, GstAmt, NetAmt, formar_id, getmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_payment);

        radio_online = findViewById(R.id.radio_online);
        tv_number = findViewById(R.id.tv_number);
        submit = findViewById(R.id.submit);
        tv_amt = findViewById(R.id.tv_amt);
        updae_acar = findViewById(R.id.updae_acar);

        try {
            Intent intent = getIntent();
            // mobile=intent.getStringExtra("mobile");
            formar_id = intent.getStringExtra("farmar_id");
            Amount = getIntent().getStringExtra("Amount");
            GstAmt = getIntent().getStringExtra("GstAmt");
            NetAmt = getIntent().getStringExtra("NetAmt");
            getphone = getIntent().getStringExtra("mobile");
            getmail = getIntent().getStringExtra("mail");
            getname = getIntent().getStringExtra("name");
            tv_amt.setText("Rs. " + Amount + " + 18% Gst - " + GstAmt + " = Total Amount: " + NetAmt);
        } catch (Exception e) {

        }

        tv_number.setText("Mob. " + getphone);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio_online.isChecked()) {

                    Toast.makeText(FirstTimePayment.this, "Instamojo Payment Gateway...", Toast.LENGTH_SHORT).show();
                    callInstamojoPay(getmail, getphone, NetAmt, "Registration", getname);
                    // startPayment();//razor pay payment gateway

                } else {
                    Toast.makeText(FirstTimePayment.this, "Please select payment mode", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updae_acar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(FirstTimePayment.this, UpdateAcarActivity.class);
                i.putExtra("farmar_id", formar_id);
                startActivity(i);

            }
        });

    }

    private void callInstamojoPay(String email, String getphone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", getphone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("insta_error", e.toString());
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;

    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), "Payment Success: " + response, Toast.LENGTH_LONG).show();

                Payment_online(response);//after success payment send data to server
                //then call intent

            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }

    private void Payment_online(String response) {

        final ProgressDialog progressDialog = new ProgressDialog(FirstTimePayment.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        RxAndroidNetworking.post(GET_farmer_payment)
                .addBodyParameter("farmer_id", formar_id)
                .addBodyParameter("amount", NetAmt)
                .addBodyParameter("transection_no", response)
                //.addBodyParameter("status", "Success")
                .build()
                .getObjectObservable(FarmerRegistrationModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FarmerRegistrationModel>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        // do anything onComplete
                        Log.e("reg_rescomp", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Log.e("reg_error", e.toString());
                        // handle error
                    }

                    @Override
                    public void onNext(FarmerRegistrationModel user) {
                        progressDialog.dismiss();
                        // do anything with response
                        try {
                            // Log.e("reg_responce",user.getData().getFullName());
                            Toast.makeText(FirstTimePayment.this, "" + user.getResponce(), Toast.LENGTH_SHORT).show();
//                            sessionManagement.logoutSession();
//                            sessionManagement.createLoginSession("","","","","","","","","","");
//                            sessionManagement.setLogin(false);
                            sessionManagement = new Session_management(FirstTimePayment.this);
                            sessionManagement.setLogin(true);
                            sessionManagement.createLoginSession(user.getData().getFarmerId(), user.getData().getEmail(), user.getData().getFullName()
                                    , user.getData().getMobileNo(), user.getData().getProfile_image(), "", "", "", "", "");


                            Intent wallet = new Intent(FirstTimePayment.this, MainActivity.class);
                            startActivity(wallet);
                            finish();

                        } catch (Exception e) {
                            Log.e("categ_catch", e.getMessage());
                        }

                    }
                });
    }
}
