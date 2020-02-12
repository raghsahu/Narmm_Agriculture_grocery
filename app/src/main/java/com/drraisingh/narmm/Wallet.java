package com.drraisingh.narmm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easebuzz.payment.kit.PWECouponsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.drraisingh.narmm.Config.BaseURL;

import cn.refactor.lib.colordialog.PromptDialog;
import datamodels.StaticDataModel;
import com.drraisingh.narmm.util.Session_management;
import com.drraisingh.narmm.util.Utilities;

public class Wallet extends AppCompatActivity  {
EditText amount;
    ProgressDialog progressDoalog;
    SharedPreferences sharedPreferences1,sh;
Button five,ten,fiveten,paynow;
    TextView payment,userpay;
    Double totalamount;
    String orderId;
    HashMap<String, String> paramMap;
    String result;
    private String getuser_id = "";
    private Session_management sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
        amount=(EditText)findViewById(R.id.amount);
        sessionManagement = new Session_management(this);
        five=(Button)findViewById(R.id.five);
        userpay=(TextView)findViewById(R.id.userpay);
        ten=(Button)findViewById(R.id.ten);
        fiveten=(Button)findViewById(R.id.fiveten);
        Intent  intent=getIntent();
        amount.setText(intent.getStringExtra("data"));
        new Regisste().execute();
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(five.getText().toString().replace("+",""));
            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(ten.getText().toString().replace("+",""));
            }
        });

        fiveten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(fiveten.getText().toString().replace("+",""));
            }
        });






    }

    public void generateCheckSum(View view) {
        String getemail = sessionManagement.getUserDetails().get(BaseURL.KEY_EMAIL);
        String getimage = sessionManagement.getUserDetails().get(BaseURL.KEY_IMAGE);
        String getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
        String getphone = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
        String getpin = sessionManagement.getUserDetails().get(BaseURL.KEY_PINCODE);
        String gethouse = sessionManagement.getUserDetails().get(BaseURL.KEY_HOUSE);
        String getsocity = sessionManagement.getUserDetails().get(BaseURL.KEY_SOCITY_ID);
        String getsocity_name = sessionManagement.getUserDetails().get(BaseURL.KEY_SOCITY_NAME);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            //String currentDateandTime = sdf.format(new Date());
            String currentDateandTime=String.valueOf(((new Date().getTime() / 1000L) % Integer.MAX_VALUE));
            if(!Utilities.isConnectingToInternet(Wallet.this)){
                // showPromptDlg();
            }
            else if(amount.getText().toString().equals("")){
                amount.setError("Please Insert Amount");
            }
            else  if(amount.getText().toString().equals("0")){
                amount.setError("Please Insert Amount");
            }

            else {
                Float txnAmount= Float.valueOf(amount.getText().toString());
                Intent intentProceed = new Intent(Wallet.this, PWECouponsActivity.class);
                intentProceed.putExtra("trxn_id",currentDateandTime);
                intentProceed.putExtra("trxn_amount",txnAmount);
                intentProceed.putExtra("trxn_prod_info","dddd");
                intentProceed.putExtra("trxn_firstname",getname);
                intentProceed.putExtra("trxn_email_id",getemail);
                intentProceed.putExtra("trxn_phone",getphone);
                intentProceed.putExtra("trxn_key","GZEYYW8AY7");
                intentProceed.putExtra("trxn_udf1","");
                intentProceed.putExtra("trxn_udf2","");
                intentProceed.putExtra("trxn_udf3","");
                intentProceed.putExtra("trxn_udf4","");
                intentProceed.putExtra("trxn_udf5","");
                intentProceed.putExtra("trxn_address1",gethouse);
                intentProceed.putExtra("trxn_address2",getsocity_name);
                intentProceed.putExtra("trxn_city","");
                intentProceed.putExtra("trxn_state","");
                intentProceed.putExtra("trxn_country","");
                intentProceed.putExtra("trxn_zipcode",getpin);
                intentProceed.putExtra("trxn_is_coupon_enabled","");
                intentProceed.putExtra("trxn_salt","1LXZ9HB28C");
                intentProceed.putExtra("unique_id",currentDateandTime);
                intentProceed.putExtra("pay_mode","test");
                startActivityForResult(intentProceed, StaticDataModel.PWE_REQUEST_CODE);


            }

























        /*//getting the tax amount first.


        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating the retrofit api service
        Api apiService = retrofit.create(Api.class);

        //creating paytm object
        //containing all the values required
        final Paytm paytm = new Paytm(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                amount.getText().toString(),
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getChannelId(),
                paytm.getTxnAmount(),
                paytm.getWebsite(),
                paytm.getCallBackUrl(),
                paytm.getIndustryTypeId()
        );

        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                //once we get the checksum we will initiailize the payment.
                //the method is taking the checksum we got and the paytm object as the parameter
                initializePaytmPayment(response.body().getChecksumHash(), paytm);
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {

            }
        });*/
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        result=data.getStringExtra("result");


        if(result.equals("payment_successfull")){

            String response = data.getStringExtra("payment_response");
            try {
                JSONObject jsonObject=new JSONObject(response);
                String bname = jsonObject.getString("name_on_card");
                String bank_ref_num = jsonObject.getString("bank_ref_num");
                String udf3 = jsonObject.getString("udf3");
                String hash = jsonObject.getString("hash");
                String firstname = jsonObject.getString("firstname");
                String net_amount_debit = jsonObject.getString("net_amount_debit");
                String payment_source = jsonObject.getString("payment_source");
                String surl = jsonObject.getString("surl");
                String error_Message = jsonObject.getString("error_Message");
                String issuing_bank = jsonObject.getString("issuing_bank");
                String cardCategory = jsonObject.getString("cardCategory");
                String phone = jsonObject.getString("phone");
                String easepayid = jsonObject.getString("easepayid");

                String cardnum = jsonObject.getString("cardnum");
                String key = jsonObject.getString("key");
                String udf8 = jsonObject.getString("udf8");
                String unmappedstatus = jsonObject.getString("unmappedstatus");
                String PG_TYPE = jsonObject.getString("PG_TYPE");
                String addedon = jsonObject.getString("addedon");


                String card_type = jsonObject.getString("card_type");
                String amount = jsonObject.getString("amount");



                String deduction_percentage = jsonObject.getString("deduction_percentage");
                String orderid = jsonObject.getString("txnid");
                String txnid = jsonObject.getString("txnid");



                new FetchSamplePaper(amount,txnid).execute();


                //loadBulkshopdata(error_Message,net_amount_debit,orderid,amount,addedon,txnid,"online",PG_TYPE,unmappedstatus,card_type);


            }catch (Exception e){

            }





        }else if(result.equals("user_cancelled")) {

            PromptDialog promptDialog=  new PromptDialog(Wallet.this);
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Cancelled");
            promptDialog.setContentText("user_cancelled");
            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("payment_failed")) {

            PromptDialog promptDialog=  new PromptDialog(Wallet.this);
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Failure");
            promptDialog.setContentText("payment_failed");
            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("txn_session_timeout")) {

            PromptDialog promptDialog=  new PromptDialog(Wallet.this);
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction session timeout");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("back_pressed")) {

            PromptDialog promptDialog=  new PromptDialog(Wallet.this);
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Failure");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("invalid_input_data")) {

            PromptDialog promptDialog=  new PromptDialog(Wallet.this);
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Invalid Input Data");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("trxn_not_allowed")) {

            PromptDialog promptDialog=  new PromptDialog(Wallet.this);
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Not allowed");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }
        else if(result.equals("bank_back_pressed")) {

            PromptDialog promptDialog=  new PromptDialog(Wallet.this);
            promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
            promptDialog.setAnimationEnable(true);
            promptDialog.setTitleText("Transaction Not allowed");

            promptDialog.setCancelable(false);
            promptDialog.setCanceledOnTouchOutside(false);
            promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                @Override
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();

                }
            }).show();

        }





    }


    public class FetchSamplePaper extends AsyncTask<String, String, String> {

        String result = "";
        String amt,txnid;
        ProgressDialog dialog;

        public FetchSamplePaper(String amount, String txnid) {
            this.amt=amount;
            this.txnid=txnid;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Wallet.this);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            try{
                String urlParameters =
                        "user_id=" + URLEncoder.encode(getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID), "UTF-8")+
                                "&name=" + URLEncoder.encode( sessionManagement.getUserDetails().get(BaseURL.KEY_NAME), "UTF-8")+
                                "&phone_no=" + URLEncoder.encode(sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE), "UTF-8")+
                                "&amount=" + URLEncoder.encode(amt, "UTF-8")+
                                "&trnx_id=" + URLEncoder.encode(txnid, "UTF-8")+
                                "&trnx_status=" + URLEncoder.encode("success", "UTF-8");
                ;
                result = Utilities.postParamsAndfindJSON("https://harshadbhaifruitcompany.com/hfcmall/index.php/Api/updateWallet", urlParameters);
                Log.i("dataff",result.toString());

            }catch (Exception e){

            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                dialog.dismiss();
                Utilities.Alert(Wallet.this, "Server problem occurred");
            } else {
                try {
                    dialog.dismiss();

                    JSONObject jsonObject=new JSONObject(result);
                    Log.d("data",result.toString());
                    String status = jsonObject.getString("responce");
                    if (status.equals("true")) {
                        PromptDialog promptDialog=  new PromptDialog(Wallet.this);
                        promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS);
                        promptDialog.setAnimationEnable(true);
                        promptDialog.setTitleText("Transaction Success");

                        promptDialog.setCancelable(false);
                        promptDialog.setCanceledOnTouchOutside(false);
                        promptDialog.setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                finish();

                            }
                        }).show();

                    }


                } catch (Exception e) {

                }

                super.onPostExecute(result);
            }
        }


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

     class Regisste extends AsyncTask<String,String,String> {
         String result = null;

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

             progressDoalog = new ProgressDialog(Wallet.this);
             progressDoalog.setMax(100);
             progressDoalog.setMessage("Its loading....");
             progressDoalog.setTitle("Please Wait...");

             // show it
             progressDoalog.show();
         }

         @Override
         protected String doInBackground(String... strings) {
             try {
                 String urlParameters =
                         "user_id=" + URLEncoder.encode(sessionManagement.getUserDetails().get(BaseURL.KEY_ID), "UTF-8");
                 result = Utilities.postParamsAndfindJSON(" https://harshadbhaifruitcompany.com/hfcmall/index.php/Api/wallet?", urlParameters);
             } catch (Exception e) {
             }
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
                     JSONArray json = new JSONArray(result);
                     for (int i=0;i<=json.length();i++){
                         JSONObject jsonObject=json.getJSONObject(i);
                         String id=jsonObject.getString("id");
                         String use_id=jsonObject.getString("user_id");
                         String amt=jsonObject.getString("amount");
                         userpay.setText(amt);



                     }



                 } catch (Exception e) {

                 }


             }


         }
     }

}
