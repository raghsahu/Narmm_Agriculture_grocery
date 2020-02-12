package com.drraisingh.narmm;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.drraisingh.narmm.Adapter.NotificationAdaper;
import com.drraisingh.narmm.Model.My_notify;
import com.drraisingh.narmm.util.ConnectivityReceiver;
import com.drraisingh.narmm.util.Utilities;

public class Notification extends AppCompatActivity {
    private RecyclerView rv_mynotify;
    private List<My_notify> my_order_modelList = new ArrayList<>();
    My_notify my_notify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        rv_mynotify = (RecyclerView) findViewById(R.id.rv_mynotify);
        rv_mynotify.setLayoutManager(new LinearLayoutManager(this));
        if (ConnectivityReceiver.isConnected()) {
            new FetchSamplePaper().execute();
        } else {

        }
    }


    public class FetchSamplePaper extends AsyncTask<String, String, String> {

        String result = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Notification.this);
            dialog.setMessage("Loading..");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            try{

                result = Utilities.getRequestAndfindJSON("https://harshadbhaifruitcompany.com/hfcmall/index.php/Api/notification");
                Log.i("dataff",result.toString());

            }catch (Exception e){

            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                dialog.dismiss();
                Utilities.Alert(Notification.this, "Server problem occurred");
            } else {
                try {
                    dialog.dismiss();



                    JSONArray jsonArray= new JSONArray(result);
                    for(int i=0;i<jsonArray.length();i++)
                    {



                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        my_notify=new My_notify();
                        String id=jsonObject1.getString("id");
                        my_notify.setTitle(jsonObject1.getString("title"));
                        my_notify.setDescription(jsonObject1.getString("description"));
                        my_notify.setCdate(jsonObject1.getString("cdate"));

                        my_order_modelList.add(my_notify);


                    }


                    NotificationAdaper adapter = new NotificationAdaper(my_order_modelList);
                    rv_mynotify.setAdapter(adapter);
                    adapter.notifyDataSetChanged();





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


}
